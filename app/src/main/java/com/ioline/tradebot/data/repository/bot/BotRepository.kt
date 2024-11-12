package com.ioline.tradebot.data.repository.bot

import com.ioline.tradebot.data.models.Bot
import kotlinx.coroutines.flow.Flow

interface BotRepository {
    fun getBot(botId: String): Flow<Bot>
    fun getBots(): Flow<List<Bot>>
    suspend fun createBot(bot: Bot)
    suspend fun deleteBot(bot: Bot)
    suspend fun changeBotStatus(botId: String, isActive: Boolean): Bot?
    suspend fun refreshBots()
}
