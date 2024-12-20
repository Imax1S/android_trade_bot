package com.ioline.tradebot.domain

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val LOCAL_URL = "http://10.0.2.2:8080/"
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(LOCAL_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
