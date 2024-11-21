package com.ioline.tradebot.data.source.local

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalBotDataSourceImpl : LocalBotDataSource {
    override fun getBot(id: String): Bot? = botsMock.find { it.id == id }

    override fun getBots(): Flow<List<Bot>> = flow {
        emit(botsMock)
    }

    override suspend fun updateBot(bot: Bot) {
        val botIndex = botsMock.indexOfFirst { it.id == bot.id }
        botsMock[botIndex] = bot
    }

    override suspend fun setBots(bots: List<Bot>) {
        TODO("Not yet implemented")
    }

    override suspend fun addBot(bot: Bot) {
        botsMock.add(bot)
    }

    override suspend fun changeBotStatus(botId: String, isActive: Boolean): Bot? {
        botsMock.indexOfFirst { it.id == botId }
            .takeIf { it >= 0 }
            ?.let { index ->
                botsMock[index] = botsMock[index].copy(isActive = isActive)
            }

        return botsMock.find { it.id == botId }
    }
}

val botsMock = mutableListOf(
    Bot(
        id = "0",
        name = "Edwin Hopper 0",
        description = "similique",
        strategy = null,
        isActive = false,
        instrumentsFIGI = listOf(),
        marketEnvironment = MarketEnvironment.MARKET,
        timeSettings = null,
        result = null
    ),
    Bot(
        id = "1",
        name = "Edwin Hopper 1",
        description = "similique",
        strategy = null,
        isActive = false,
        instrumentsFIGI = listOf(),
        marketEnvironment = MarketEnvironment.MARKET,
        timeSettings = null,
        result = null
    ),
    Bot(
        id = "2",
        name = "Edwin Hopper 2",
        description = "similique",
        strategy = null,
        isActive = true,
        instrumentsFIGI = listOf(),
        marketEnvironment = MarketEnvironment.MARKET,
        timeSettings = null,
        result = null
    ),
    Bot(
        id = "3",
        name = "Edwin Hopper 3",
        description = "similique",
        strategy = null,
        isActive = false,
        instrumentsFIGI = listOf(),
        marketEnvironment = MarketEnvironment.MARKET,
        timeSettings = null,
        result = null
    ),
    Bot(
        id = "4",
        name = "Edwin Hopper 4",
        description = "similique",
        strategy = null,
        isActive = false,
        instrumentsFIGI = listOf(),
        marketEnvironment = MarketEnvironment.MARKET,
        timeSettings = null,
        result = null
    )
)