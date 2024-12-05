package com.ioline.tradebot.features.settings.presentation

internal sealed interface SettingsEffect {
    data object Close : SettingsEffect
    data object NavigateToAuthorizationInScreen : SettingsEffect
}