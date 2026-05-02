package com.example.concurrenttranslator.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.concurrenttranslator.model.api.TranslatorApiClient
import com.example.concurrenttranslator.model.livedata.TranslatorLiveData
import java.net.HttpURLConnection.HTTP_OK

class LanguagesService : Service() {
    private lateinit var handler: LanguagesServiceHandler
    private lateinit var serviceLogTag: String

    private inner class LanguagesServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            TranslatorApiClient.service.getLanguages().execute().also { response ->
                if (response.code() == HTTP_OK) {
                    response.body()?.also { languageList ->
                        TranslatorLiveData.languagesLiveData.postValue(languageList)
                    }
                } else {
                    TranslatorLiveData.errorLiveData.postValue("Erro ao carregar idiomas: ${response.code()}")
                }
            }
            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {
        super.onCreate()
        HandlerThread(this.javaClass.name).apply {
            start()
            handler = LanguagesServiceHandler(looper)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceLogTag = "${javaClass.simpleName}/$startId"
        Log.v(serviceLogTag, "Service started")
        handler.obtainMessage().also { msg ->
            msg.arg1 = startId
            handler.sendMessage(msg)
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        Log.v(serviceLogTag, "Service done")
    }
}