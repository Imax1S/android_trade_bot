package com.ioline.tradebot.domain

import com.ioline.tradebot.data.models.Instrument
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface TinkoffApi {
    @GET("/findInstrument/{id}")
    suspend fun findInstrument(@Path("id") id: String): Response<Instrument?>

}
