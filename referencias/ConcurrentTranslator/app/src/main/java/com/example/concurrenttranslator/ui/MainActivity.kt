package com.example.concurrenttranslator.ui

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.concurrenttranslator.R
import com.example.concurrenttranslator.databinding.ActivityMainBinding
import com.example.concurrenttranslator.model.livedata.TranslatorLiveData
import com.example.concurrenttranslator.service.LanguagesService
import com.example.concurrenttranslator.service.TranslateService

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val languagesServiceIntent by lazy {
        Intent(this, LanguagesService::class.java)
    }
    private var translateService: TranslateService? = null
    private val translateServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            translateService = (service as TranslateService.TranslateServiceBinder).getTranslateService()
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            // NSA
        }
        override fun onBindingDied(name: ComponentName?) {
            super.onBindingDied(name)
        }
        override fun onNullBinding(name: ComponentName?) {
            super.onNullBinding(name)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        setSupportActionBar(amb.mainTb.apply { title = getString(R.string.app_name) })

        var fromLanguage = ""
        var toLanguage = ""
        val languagesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf<String>())
        with(amb){
            fromLanguageMactv.apply {
                setAdapter(languagesAdapter)
                setOnItemClickListener { _, _, _, _ -> fromLanguage = text.toString().split(" - ")[0] }
            }
            toLanguageMactv.apply {
                setAdapter(languagesAdapter)
                setOnItemClickListener { _, _, _, _ -> toLanguage = text.toString().split(" - ")[0] }
            }
            translateBtn.setOnClickListener {
                val text = inputTextEt.text.toString()
                if (text.isNotBlank() && fromLanguage.isNotBlank() && toLanguage.isNotBlank()) {
                    translateService?.translate(text, fromLanguage, toLanguage)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Por favor, preencha todos os campos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        TranslatorLiveData.languagesLiveData.observe(this) { languageList ->
            languagesAdapter.clear()
            val formattedLanguages = languageList.languages.map {
                "${it.language} - ${it.name}"
            }.sorted()
            languagesAdapter.addAll(formattedLanguages)
            languagesAdapter.getItem(0)?.also { formattedLanguage ->
                amb.fromLanguageMactv.setText(formattedLanguage, false)
                fromLanguage = formattedLanguage.split(" - ")[0]
            }
            languagesAdapter.getItem(languagesAdapter.count - 1)?.also { formattedLanguage ->
                amb.toLanguageMactv.setText(formattedLanguage, false)
               toLanguage = formattedLanguage.split(" - ")[0]
            }
        }

        TranslatorLiveData.translationResultLiveData.observe(this) { result ->
            val translatedText = if (result.data.translations.translatedText.isNotEmpty()) {
                result.data.translations.translatedText.joinToString("\n")
            } else {
                "Nenhuma tradução disponível"
            }
            amb.outputTextTv.text = android.text.Editable.Factory.getInstance().newEditable(translatedText)
        }
        TranslatorLiveData.errorLiveData.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }

        startService(languagesServiceIntent)
    }
    override fun onStart() {
        super.onStart()
        Intent(this, TranslateService::class.java).also { intent ->
            bindService(intent, translateServiceConnection, BIND_AUTO_CREATE)
        }
    }
    override fun onStop() {
        super.onStop()
        unbindService(translateServiceConnection)
    }
    override fun onDestroy() {
        super.onDestroy()
        stopService(languagesServiceIntent)
    }
}