package com.example.concurrenttranslator.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.concurrenttranslator.model.api.TranslationRequest
import com.example.concurrenttranslator.model.api.TranslatorApiClient
import com.example.concurrenttranslator.model.livedata.TranslatorLiveData
import java.net.HttpURLConnection.HTTP_OK

class TranslateService : Service() {
    private val translateServiceBinder = TranslateServiceBinder()
    private lateinit var handler: TranslateServiceHandler

    companion object {
        const val TEXT_PARAMETER = "text"
        const val SOURCE_PARAMETER = "source"
        const val TARGET_PARAMETER = "target"
    }

    inner class TranslateServiceBinder : Binder() {
        fun getTranslateService() = this@TranslateService
    }

    private inner class TranslateServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            with(msg.data) {
                val request = TranslationRequest(
                    q = getString(TEXT_PARAMETER, ""),
                    source = getString(SOURCE_PARAMETER, ""),
                    target = getString(TARGET_PARAMETER, "")
                )

                TranslatorApiClient.service.translate(request).execute().also { response ->
                    if (response.code() == HTTP_OK) {
                        response.body()?.let { result ->
                            TranslatorLiveData.translationResultLiveData.postValue(result)
                        }
                    } else {
                        TranslatorLiveData.errorLiveData.postValue("Erro ${response.code()}")
                        Log.e(
                            "TranslateService",
                            "Erro na tradução",
                            Exception("Código de erro: ${response.code()}")
                        )
                    }
                }
            }
        }
    }

    fun translate(text: String, source: String, target: String) {
        HandlerThread(this.javaClass.simpleName).apply {
            start()
            handler = TranslateServiceHandler(looper)
        }

        handler.obtainMessage().apply {
            data = Bundle().apply {
                putString(TEXT_PARAMETER, text)
                putString(SOURCE_PARAMETER, source)
                putString(TARGET_PARAMETER, target)
            }
            handler.sendMessage(this)
        }
    }

    override fun onBind(intent: Intent): IBinder {
        Log.v(this.javaClass.simpleName, "Service started.")
        return translateServiceBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.v(this.javaClass.simpleName, "Service done.")
        return super.onUnbind(intent)
    }
}