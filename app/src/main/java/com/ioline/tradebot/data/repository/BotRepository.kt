package com.ioline.tradebot.data.repository

import com.ioline.tradebot.data.models.Bot
import kotlinx.coroutines.flow.Flow

interface BotRepository {
    fun getBot(botId: String): Flow<WorkResult<Bot>>
    fun getBots(): Flow<WorkResult<List<Bot>>>
    suspend fun createBot(bot: Bot)
    suspend fun deleteBot(bot: Bot)
    suspend fun updateBot(bot: Bot)
    suspend fun refreshBots()

}
