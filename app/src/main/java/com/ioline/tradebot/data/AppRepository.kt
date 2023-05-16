package com.ioline.tradebot.data

import android.util.Log
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.HistoricalResult
import com.ioline.tradebot.data.models.Instrument
import com.ioline.tradebot.domain.TinkoffApi

class AppRepository(private val tinkoffApi: TinkoffApi) {

    suspend fun findInstrument(id: String): Instrument? {
        var instrument: Instrument? = null
        try {
            val response = tinkoffApi.findInstrument(id)
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
            val response = tinkoffApi.getPrice(id)
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
            val response = tinkoffApi.createBot(bot)
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
            val response = tinkoffApi.runBot(bot.id)
            if(response.isSuccessful) {
                result = response.body()
            }
        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }

        return result
    }
}
