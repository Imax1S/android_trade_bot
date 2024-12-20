package com.ioline.tradebot.features.bot.creation.screens.environment.presentation

internal sealed class EnvironmentSettingsEffect {
    data object NavigateToHome : EnvironmentSettingsEffect()
    data object Back : EnvironmentSettingsEffect()
    data object ShowInstruction : EnvironmentSettingsEffect()
}