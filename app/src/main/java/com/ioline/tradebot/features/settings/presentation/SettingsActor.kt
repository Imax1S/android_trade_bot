package com.ioline.tradebot.features.settings.presentation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

internal class SettingsActor @Inject constructor(
    // your dependencies
) : Actor<SettingsCommand, SettingsEvent> {

    override fun execute(
        command: SettingsCommand
    ): Flow<SettingsEvent> = flow {
        when (command) {
            SettingsCommand.LoadData -> {}
            SettingsCommand.LogOutUser -> {

            }
            is SettingsCommand.ValidateToken -> {}
        }
    }
}