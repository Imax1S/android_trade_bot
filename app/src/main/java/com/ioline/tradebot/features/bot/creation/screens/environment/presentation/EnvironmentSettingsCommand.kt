package com.ioline.tradebot.features.bot.creation.screens.environment.presentation

import com.ioline.tradebot.data.models.Bot
import java.util.Date

internal sealed class EnvironmentSettingsCommand {
    data class LoadData(val botId: String) : EnvironmentSettingsCommand()
    data class ValidateToken(val token: String) : EnvironmentSettingsCommand()
    data class ValidateDate(val date: Date) : EnvironmentSettingsCommand()
    data class UpdateBotEnvironment(val bot: Bot) : EnvironmentSettingsCommand()
}