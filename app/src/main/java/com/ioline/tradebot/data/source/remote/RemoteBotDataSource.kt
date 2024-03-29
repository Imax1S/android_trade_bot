package com.ioline.tradebot.data.source.remote

import com.ioline.tradebot.data.models.Bot

interface RemoteBotDataSource {
    suspend fun getBots(): List<Bot>
    suspend fun addBot(bot: Bot): Bot
}
