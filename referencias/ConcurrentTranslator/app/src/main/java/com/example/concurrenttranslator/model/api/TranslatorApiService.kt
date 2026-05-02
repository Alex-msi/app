package com.example.concurrenttranslator.model.api

import com.example.concurrenttranslator.model.domain.LanguageList
import com.example.concurrenttranslator.model.domain.TranslationResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface TranslatorApiService {
    @Headers(
        "x-rapidapi-host: deep-translate1.p.rapidapi.com",
        "x-rapidapi-key: eb5e80d8camsh95faeb01e1ce01ep17b49ejsn712844d4532d",
        "Content-Type: application/json"
    )
    @GET("translate/v2/languages")
    fun getLanguages(): Call<LanguageList>

    @Headers(
        "x-rapidapi-host: deep-translate1.p.rapidapi.com",
        "x-rapidapi-key: eb5e80d8camsh95faeb01e1ce01ep17b49ejsn712844d4532d",
        "Content-Type: application/json"
    )
    @POST("translate/v2")
   fun translate(@Body request: TranslationRequest): Call<TranslationResult>
}

data class TranslationRequest(
   val q: String,
   val source: String,
   val target: String
)