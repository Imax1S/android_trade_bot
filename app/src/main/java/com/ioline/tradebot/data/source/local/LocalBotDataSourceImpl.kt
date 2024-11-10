package com.ioline.tradebot.data.source.local

import com.ioline.tradebot.data.models.Bot
import kotlinx.coroutines.flow.Flow

class LocalBotDataSourceImpl : LocalBotDataSource {
    override fun getBot(): Flow<Bot> {
        TODO("Not yet implemented")
    }

    override fun getBots(): Flow<List<Bot>> {
        TODO("Not yet implemented")
    }

    override suspend fun setBots(bots: List<Bot>) {
        TODO("Not yet implemented")
    }

    override suspend fun addBot(bot: Bot) {
        TODO("Not yet implemented")
    }
}