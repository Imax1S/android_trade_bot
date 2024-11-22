package com.ioline.tradebot.data.source.remote

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.HistoricalResult
import com.ioline.tradebot.domain.TradeBotApi
import javax.inject.Inject

class RemoteBotDataSourceImpl @Inject constructor(
    private val tradeBotApi: TradeBotApi
) : RemoteBotDataSource {
    override suspend fun getBots(): List<Bot> {
        val result = tradeBotApi.getAllBots()
        return when {
            result.isSuccessful -> result.body() ?: emptyList()
            else -> emptyList()
        }
    }

    override suspend fun addBot(bot: Bot): Bot? {
        val result = tradeBotApi.createBot(bot)

        return if (result.isSuccessful) {
            result.body()
        } else {
            null
        }
    }

    override suspend fun getBot(id: String): Bot? {
        val result = tradeBotApi.getBot(id)

        return if (result.isSuccessful) {
            result.body()
        } else {
            null
        }
    }

    override suspend fun runBot(id: String): HistoricalResult? {
        val result = tradeBotApi.runBot(id)

        return if (result.isSuccessful) {
            result.body()
        } else {
            null
        }
    }
}