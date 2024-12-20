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