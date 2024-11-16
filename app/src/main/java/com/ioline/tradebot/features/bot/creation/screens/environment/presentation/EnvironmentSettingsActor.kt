package com.ioline.tradebot.features.bot.creation.screens.environment.presentation

import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsEvent.Internal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

internal class EnvironmentSettingsActor @Inject constructor(
    // your dependencies
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
        }
    }
}