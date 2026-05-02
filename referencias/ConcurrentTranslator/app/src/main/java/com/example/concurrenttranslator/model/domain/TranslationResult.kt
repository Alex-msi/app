package com.example.concurrenttranslator.model.domain

data class TranslationResult(
    val data: Data,
    val status: String = "OK"
) {
    data class Data(
        val translations: Translations
    ) {
        data class Translations(
            val translatedText: List<String>
        )
    }
}
