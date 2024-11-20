package com.ioline.tradebot.features.home.presentation

import com.ioline.tradebot.data.repository.bot.BotRepository
import com.ioline.tradebot.features.home.presentation.HomeCommand
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject
import com.ioline.tradebot.features.home.presentation.HomeCommand as Command
import com.ioline.tradebot.features.home.presentation.HomeEvent.Domain as DomainEvent

internal class HomeActor @Inject constructor(
    private val botRepository: BotRepository,
) : Actor<Command, DomainEvent> {
    override fun execute(
        command: Command
    ): Flow<DomainEvent> = when (command) {
        Command.LoadData -> loadData()
        is HomeCommand.ChangeBotStatus -> changeBotStatus(command.botId, command.isActive)
    }

    private fun loadData(): Flow<DomainEvent> {
        return flow {
            botRepository.getBots()
                .catch { emit(DomainEvent.Error(it)) }
                .collect { emit(DomainEvent.LoadData(it)) }
        }
    }

    private fun changeBotStatus(botId: String, isActive: Boolean): Flow<DomainEvent> {
        return flow {
            botRepository.changeBotStatus(
                botId,
                isActive
            )?.let { updatedBot ->
                emit(HomeEvent.Domain.UpdateBot(updatedBot))
            }
        }
    }
}
