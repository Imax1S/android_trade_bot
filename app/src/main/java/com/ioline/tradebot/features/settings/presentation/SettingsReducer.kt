package com.ioline.tradebot.features.settings.presentation

import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object SettingsReducer : ScreenDslReducer<
        SettingsEvent,
        SettingsEvent.Ui,
        SettingsEvent.Domain,
        SettingsState,
        SettingsEffect,
        SettingsCommand
        >(SettingsEvent.Ui::class, SettingsEvent.Domain::class) {

    override fun Result.internal(event: SettingsEvent.Domain) {
        when (event) {
            SettingsEvent.Domain.Init -> commands {
                SettingsCommand.LoadData
            }
        }
    }

    override fun Result.ui(event: SettingsEvent.Ui) {
        when (event) {
            SettingsEvent.Ui.ChangeUiTheme -> {
                state { copy(isDarkTheme = !isDarkTheme) }
                effects {
                    +SettingsEffect.ChangeTheme
                }
            }
            is SettingsEvent.Ui.EnterToken -> commands {
                +SettingsCommand.ValidateToken(event.token)
            }
            SettingsEvent.Ui.LogIn -> effects {
                +SettingsEffect.NavigateToAuthorizationInScreen
            }
            SettingsEvent.Ui.LogOut -> commands {
                state { copy(user = null) }
            }
        }
    }
}