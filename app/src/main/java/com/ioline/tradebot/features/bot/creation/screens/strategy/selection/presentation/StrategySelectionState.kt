package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation

import com.ioline.tradebot.data.models.strategy.Strategy

internal data class StrategySelectionState(
    val botId: String,
    val strategies: List<Strategy> = emptyList()
)
