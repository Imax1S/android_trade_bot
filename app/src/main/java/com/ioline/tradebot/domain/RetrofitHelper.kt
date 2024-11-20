package com.ioline.tradebot.domain

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val localUrl = "http://10.0.2.2:8080/"
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(localUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }
}
