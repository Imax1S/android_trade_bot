package com.ioline.tradebot.data.source.local

import com.ioline.tradebot.data.models.Bot
import kotlinx.coroutines.flow.Flow

interface LocalBotDataSource {
    fun getBot(id: String): Bot?
    fun getBots(): Flow<List<Bot>>
    suspend fun updateBot(bot: Bot)
    suspend fun setBots(bots: List<Bot>)
    suspend fun addBot(bot: Bot)
    suspend fun changeBotStatus(botId: String, isActive: Boolean): Bot?
}
