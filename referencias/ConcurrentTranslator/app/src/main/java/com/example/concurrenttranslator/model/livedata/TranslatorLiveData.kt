package com.example.concurrenttranslator.model.livedata

import androidx.lifecycle.MutableLiveData
import com.example.concurrenttranslator.model.domain.LanguageList
import com.example.concurrenttranslator.model.domain.TranslationResult

object TranslatorLiveData {
    val languagesLiveData = MutableLiveData<LanguageList>()
    val translationResultLiveData = MutableLiveData<TranslationResult>()
    val errorLiveData = MutableLiveData<String>()
}