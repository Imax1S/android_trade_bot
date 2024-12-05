package com.ioline.tradebot.features.settings.presentation

import com.ioline.tradebot.data.models.User

internal data class SettingsState(
    val user: User? = null,
    val tokenError: Boolean = false,
    val isDarkTheme: Boolean = true
)

internal sealed interface SettingsCommand {
    data class ValidateToken(val token: String) : SettingsCommand

    data object ChangeUiTheme : SettingsCommand
    data object LogOutUser : SettingsCommand
    data object LoadData : SettingsCommand
}