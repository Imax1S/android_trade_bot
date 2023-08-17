package com.ioline.tradebot.data.source.local

import com.ioline.tradebot.data.models.Bot
import kotlinx.coroutines.flow.Flow

interface LocalBotDataSource {
    fun getBot(): Flow<Bot>
    fun getBots(): Flow<List<Bot>>
    suspend fun setBots(bots: List<Bot>)
    suspend fun addBot(bot: Bot)
}
