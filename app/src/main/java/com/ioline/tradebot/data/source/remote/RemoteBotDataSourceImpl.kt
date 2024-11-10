package com.ioline.tradebot.data.source.remote

import com.ioline.tradebot.data.models.Bot

class RemoteBotDataSourceImpl : RemoteBotDataSource {
    override suspend fun getBots(): List<Bot> {
        TODO("Not yet implemented")
    }

    override suspend fun addBot(bot: Bot): Bot {
        TODO("Not yet implemented")
    }
}