package com.ioline.tradebot.data.repository.bot

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.source.local.LocalBotDataSource
import com.ioline.tradebot.data.source.remote.RemoteBotDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BotRepositoryImpl @Inject constructor(
    private val localDataSource: LocalBotDataSource,
    private val remoteDataSource: RemoteBotDataSource
) : BotRepository {
    override fun getBot(botId: String): Flow<Bot> {
        return localDataSource.getBot()
    }

    override fun getBots(): Flow<List<Bot>> = flow {
        emit(remoteDataSource.getBots())
    }

    override suspend fun createBot(bot: Bot) {
        val botWithId = remoteDataSource.addBot(bot)
        localDataSource.addBot(botWithId)
    }

    override suspend fun deleteBot(bot: Bot) {
        TODO("Not yet implemented")
    }

    override suspend fun changeBotStatus(botId: String, isActive: Boolean): Bot? {
        return localDataSource.changeBotStatus(botId, isActive)
    }

    override suspend fun refreshBots() {
        val bots = remoteDataSource.getBots()
        localDataSource.setBots(bots)
    }
}
