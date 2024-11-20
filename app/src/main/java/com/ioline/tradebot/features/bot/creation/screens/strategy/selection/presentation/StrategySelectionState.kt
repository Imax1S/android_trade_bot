package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation

import com.ioline.tradebot.data.models.strategy.Strategy
import com.ioline.tradebot.data.models.strategy.StrategyType

internal data class StrategySelectionState(
    val botId: String,
    val strategies: List<Strategy> = emptyList(),
    val randomData: List<Int> = emptyList(),
    val selectedStrategy: StrategyType = StrategyType.EMA,
    val MAPeriod1: Float = 0f,
    val MAPeriod2: Float = 0f,
)
