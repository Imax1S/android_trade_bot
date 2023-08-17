package com.ioline.tradebot.data.source.local

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.repository.WorkResult
import kotlinx.coroutines.flow.Flow

interface LocalBotDataSource {
    fun getBot(): Flow<WorkResult<Bot>>
    fun getBots(): Flow<WorkResult<List<Bot>>>
    suspend fun setBots(bots: List<Bot>)
    suspend fun addBot(bot: Bot)
}
