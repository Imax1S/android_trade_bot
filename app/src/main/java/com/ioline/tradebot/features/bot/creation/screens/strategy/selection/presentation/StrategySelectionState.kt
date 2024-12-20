package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation

import com.ioline.tradebot.data.models.strategy.Strategy
import com.ioline.tradebot.data.models.strategy.StrategyType

internal data class StrategySelectionState(
    val botId: String,
    val strategies: List<Strategy> = emptyList(),
    val randomData: List<Int> = emptyList(),
    val selectedStrategy: StrategyType = StrategyType.EMA,
    val maPeriod1: Int = 0,
    val maPeriod2: Int = 0,
    val rsiPeriod: Int = 14,
    val overboughtThreshold: Int = 70,
    val oversoldThreshold: Int = 30,
    val stopLoss: Int = 10,
    val stopGrowth: Int = 10
)
