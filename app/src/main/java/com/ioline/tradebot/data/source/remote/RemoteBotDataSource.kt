package com.ioline.tradebot.data.source.remote

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.HistoricalResult

interface RemoteBotDataSource {
    suspend fun getBots(): List<Bot>
    suspend fun addBot(bot: Bot): Bot?
    suspend fun getBot(id: String): Bot?
    suspend fun runBot(id: String): HistoricalResult?
}