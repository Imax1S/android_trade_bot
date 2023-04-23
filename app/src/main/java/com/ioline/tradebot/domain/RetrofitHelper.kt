package com.ioline.tradebot.domain

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val localUrl = ""
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(localUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }
}