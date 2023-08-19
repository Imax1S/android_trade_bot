package com.ioline.tradebot.data.repository.bot

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.source.local.LocalBotDataSource
import com.ioline.tradebot.data.source.remote.RemoteBotDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BotRepositoryImpl @Inject constructor(
    private val localDataSource: LocalBotDataSource,
    private val remoteDataSource: RemoteBotDataSource
) : BotRepository {
    override fun getBot(botId: String): Flow<Bot> {
        return localDataSource.getBot()
    }

    override fun getBots(): Flow<List<Bot>> {
        return localDataSource.getBots()
    }

    override suspend fun createBot(bot: Bot) {
        val botWithId = remoteDataSource.addBot(bot)
        localDataSource.addBot(botWithId)
    }

    override suspend fun deleteBot(bot: Bot) {
        TODO("Not yet implemented")
    }

    override suspend fun updateBot(bot: Bot): Bot {
        TODO("Not yet implemented")
    }

    override suspend fun refreshBots() {
        val bots = remoteDataSource.getBots()
        localDataSource.setBots(bots)
    }
}
