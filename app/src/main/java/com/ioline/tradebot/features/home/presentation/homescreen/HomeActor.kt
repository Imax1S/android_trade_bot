package com.ioline.tradebot.features.home.presentation.homescreen

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.repository.bot.BotRepository
import com.ioline.tradebot.features.home.presentation.homescreen.HomeCommand
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject
import com.ioline.tradebot.features.home.presentation.homescreen.HomeCommand as Command
import com.ioline.tradebot.features.home.presentation.homescreen.HomeEvent.Domain as DomainEvent


internal class HomeActor @Inject constructor(
    private val botRepository: BotRepository,
) : Actor<Command, DomainEvent> {
    override fun execute(
        command: Command
    ): Flow<DomainEvent> = when (command) {
        Command.LoadData -> loadData()
        is HomeCommand.SwitchBotWorking -> updateBot(command.botId, command.isWorking)
    }

    private fun loadData(): Flow<DomainEvent> {
        return flow {
            botRepository.getBots()
                .catch { emit(DomainEvent.Error(it)) }
                .collect { emit(DomainEvent.LoadData(it)) }
        }
    }

    private fun updateBot(botId: String, working: Boolean): Flow<DomainEvent> {
        return flow {
            botRepository.updateBot(
                Bot(
                    name = "",
                    marketEnvironment = MarketEnvironment.MARKET
                )
            ) //todo pass just id (then create new function in bot rep.) or bot
        }
    }
}
