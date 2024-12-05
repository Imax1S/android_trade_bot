package com.ioline.tradebot.features.settings.presentation

internal sealed interface SettingsEvent {
    sealed interface Domain : SettingsEvent {
        data object Init : Domain
    }

    sealed interface Ui : SettingsEvent {
        data object LogIn : Ui
        data object LogOut : Ui
        data class EnterToken(val token: String) : Ui
        data object ChangeUiTheme : Ui
    }
}