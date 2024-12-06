package com.ioline.tradebot.domain

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.HistoricalResult
import com.ioline.tradebot.data.models.Instrument
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TradeBotApi {
    @GET("/findInstrument/{id}")
    suspend fun findInstrument(@Path("id") id: String): Response<Instrument?>

    @GET("/findInstruments/{text}")
    suspend fun findInstruments(@Path("text") text: String): Response<List<Instrument>>

    @GET("/getPrice/{id}")
    suspend fun getPrice(@Path("id") id: String): Response<String>

    @POST("/tradeBot/createBot")
    suspend fun createBot(@Body bot: Bot): Response<Bot>

    @POST("/tradeBot/run/{id}")
    suspend fun runBot(@Path("id") id: String): Response<HistoricalResult>

    @GET("/tradeBot/allBots")
    suspend fun getAllBots(): Response<List<Bot>>

    @GET("/tradeBot/bot/{id}")
    suspend fun getBot(@Path("id") id: String): Response<Bot>
}
