package com.example.concurrenttranslator.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TranslatorApiClient {
    private const val BASE_URL = "https://deep-translate1.p.rapidapi.com/language/"

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        GsonConverterFactory.create()
    ).build()

    val service: TranslatorApiService = retrofit.create(TranslatorApiService::class.java)
}