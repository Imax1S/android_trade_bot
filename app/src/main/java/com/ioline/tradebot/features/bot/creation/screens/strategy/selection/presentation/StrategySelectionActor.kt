package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation

import com.ioline.tradebot.data.repository.bot.BotRepository
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionCommand
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent.Domain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionCommand as Command

internal class StrategySelectionActor @Inject constructor(
    private val botRepository: BotRepository
) : Actor<Command, Domain> {
    override fun execute(
        command: Command
    ): Flow<Domain> = flow {
        when (command) {
            is StrategySelectionCommand.UpdateBotStrategy -> {
                val bot = botRepository.getBotLocally(command.botId)
                bot?.let {
                    botRepository.updateBotLocally(
                        it.copy(
                            strategy = command.strategy
                        )
                    )
                    emit(Domain.Next(bot.id))
                }
            }
        }
    }
}