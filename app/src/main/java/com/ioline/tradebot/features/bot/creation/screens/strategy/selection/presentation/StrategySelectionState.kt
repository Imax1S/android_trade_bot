package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.strategy.Strategy

internal data class StrategySelectionState(
    val bot: Bot,
    val strategies: List<Strategy> = emptyList()
)
