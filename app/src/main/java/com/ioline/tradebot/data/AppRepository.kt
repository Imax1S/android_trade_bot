package com.ioline.tradebot.data

import android.util.Log
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.HistoricalResult
import com.ioline.tradebot.data.models.Instrument
import com.ioline.tradebot.domain.TradeBotApi

class AppRepository(private val tradeBotApi: TradeBotApi) {

    suspend fun findInstrument(id: String): Instrument? {
        var instrument: Instrument? = null
        try {
            val response = tradeBotApi.findInstrument(id)
            if (response.isSuccessful) {
                instrument = response.body()
            }
        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }

        return instrument
    }

    suspend fun getPrice(id: String): Double {
        var price = 0.0
        try {
            val response = tradeBotApi.getPrice(id)
            if (response.isSuccessful) {
                price = response.body()?.toDoubleOrNull() ?: 0.0
            }
        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }

        return price
    }

    suspend fun createBot(bot: Bot): Bot? {
        var newBot: Bot? = null

        try {
            val response = tradeBotApi.createBot(bot)
            if (response.isSuccessful) {
                newBot = response.body()
            }
        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }
        return newBot
    }

    suspend fun runBot(bot: Bot): HistoricalResult? {
        var result: HistoricalResult? = null

        try {
            val response = tradeBotApi.runBot(bot.id)
            if(response.isSuccessful) {
                result = response.body()
            }
        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }

        return result
    }
}
