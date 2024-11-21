package com.ioline.tradebot.features.bot.creation.screens.environment.presentation

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment
import java.util.Date

internal data class EnvironmentSettingsState(
    val type: MarketEnvironment,
    val bot: Bot? = null,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val token: String
)

internal sealed class EnvironmentSettingsEffect {
    data object NavigateToHome : EnvironmentSettingsEffect()
    data object Back : EnvironmentSettingsEffect()
    data object ShowInstruction : EnvironmentSettingsEffect()
}

internal sealed class EnvironmentSettingsCommand {
    data class LoadData(val botId: String) : EnvironmentSettingsCommand()
    data class ValidateToken(val token: String) : EnvironmentSettingsCommand()
    data class ValidateDate(val date: Date) : EnvironmentSettingsCommand()
    data class UpdateBotEnvironment(val bot: Bot) : EnvironmentSettingsCommand()
}