package com.example.concurrenttranslator.model.domain

data class LanguageList(
    val languages: List<Language>,
    val status: String = "OK"
) {
    data class Language(
        val language: String,
        val name: String
    )
}
