package com.ioline.tradebot.data.repository.bot

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.HistoricalResult
import kotlinx.coroutines.flow.Flow

interface BotRepository {
    suspend fun getBot(
        botId: String
    ): Flow<Bot?>
    suspend fun getBotLocally(botId: String): Bot?
    suspend fun getBots(): Flow<List<Bot>>
    suspend fun updateBotLocally(bot: Bot)
    suspend fun createBot(bot: Bot)
    suspend fun saveBotLocally(bot: Bot)
    suspend fun deleteBot(bot: Bot)
    suspend fun changeBotStatus(botId: String, isActive: Boolean): Bot?
    suspend fun refreshBots()

    suspend fun runBot(botId: String): HistoricalResult?
}