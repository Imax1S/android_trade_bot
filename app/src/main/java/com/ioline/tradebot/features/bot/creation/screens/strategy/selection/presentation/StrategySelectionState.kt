package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation

import com.ioline.tradebot.data.models.strategy.Strategy
import com.ioline.tradebot.data.models.strategy.StrategyType

internal data class StrategySelectionState(
    val botId: String,
    val strategies: List<Strategy> = emptyList(),
    val randomData: List<Int> = emptyList(),
    val selectedStrategy: StrategyType = StrategyType.EMA,
    val MAPeriod1: Int = 0,
    val MAPeriod2: Int = 0,
    val RSIPeriod: Int = 14,
    val overboughtThreshold: Int = 70,
    val oversoldThreshold: Int = 30
)
