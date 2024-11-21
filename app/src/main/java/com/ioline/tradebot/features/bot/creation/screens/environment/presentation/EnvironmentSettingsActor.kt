package com.ioline.tradebot.features.bot.creation.screens.environment.presentation

import com.ioline.tradebot.data.repository.bot.BotRepository
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsEvent.Internal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

internal class EnvironmentSettingsActor @Inject constructor(
    private val botRepository: BotRepository,
) : Actor<EnvironmentSettingsCommand, Internal> {

    override fun execute(
        command: EnvironmentSettingsCommand
    ): Flow<Internal> = flow {
        when (command) {
            is EnvironmentSettingsCommand.ValidateDate -> {
                emit(Internal.ShowDateRangeIsIncorrect)
            }
            is EnvironmentSettingsCommand.ValidateToken -> {
                emit(Internal.ShowTokenIsWrong)
            }
            is EnvironmentSettingsCommand.UpdateBotEnvironment -> {
                botRepository.createBot(command.bot)
                emit(Internal.CloseBotCreation)
            }
            is EnvironmentSettingsCommand.LoadData -> {
                emit(
                    Internal.LoadedData(
                        botRepository.getBotLocally(command.botId)
                    )
                )
            }
        }
    }
}