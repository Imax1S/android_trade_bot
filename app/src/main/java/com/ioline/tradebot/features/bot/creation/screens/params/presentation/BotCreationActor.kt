package com.ioline.tradebot.features.bot.creation.screens.params.presentation

import com.ioline.tradebot.data.repository.bot.BotRepository
import com.ioline.tradebot.data.repository.instrument.InstrumentRepository
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEvent.Domain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

internal class BotCreationActor @Inject constructor(
    private val instrumentRepository: InstrumentRepository,
    private val botRepository: BotRepository
) : Actor<BotCreationCommand, Domain> {

    override fun execute(
        command: BotCreationCommand
    ): Flow<Domain> = when (command) {
        is BotCreationCommand.SearchInstrument -> searchInstrument(command.text)
        is BotCreationCommand.SaveBotLocally -> flow {
            botRepository.saveBotLocally(command.bot)
            emit(Domain.NextPage(command.bot.id))
        }
    }

    private fun searchInstrument(text: String): Flow<Domain> {
        return flow {
            instrumentRepository.searchInstrument(text)
                .catch { emit(Domain.SearchInstrumentError) }
                .collect { emit(Domain.SearchInstrumentResult(it)) }
        }
    }
}
