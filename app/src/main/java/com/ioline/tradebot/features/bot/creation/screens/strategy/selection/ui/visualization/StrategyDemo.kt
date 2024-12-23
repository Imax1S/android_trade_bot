package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui.visualization

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.data.models.strategy.StrategyType
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionState

@Composable
internal fun StrategyDemo(
    state: StrategySelectionState,
    onEvent: (StrategySelectionEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        when (state.selectedStrategy) {
            StrategyType.EMA -> {
                StrategyMAVisualizationGraph(
                    prices = state.randomData,
                    periodForFirstEMA = state.maPeriod1,
                    periodForSecondEMA = state.maPeriod2,
                    isExponential = true
                ) { onEvent(it) }
            }
            StrategyType.MA -> {
                StrategyMAVisualizationGraph(
                    prices = state.randomData,
                    periodForSecondEMA = state.maPeriod2,
                    isExponential = false,
                    periodForFirstEMA = state.maPeriod1
                ) { onEvent(it) }
            }
            StrategyType.RSI -> {
                StrategyRSIVisualizationChart(
                    prices = state.randomData,
                    period = state.rsiPeriod,
                    overboughtThreshold = state.overboughtThreshold,
                    oversoldThreshold = state.oversoldThreshold,
                ) { onEvent(it) }
            }
            StrategyType.CUSTOM -> {
                StrategyCustomVisualization(
                    prices = state.randomData,
                    stopLoss = state.stopLoss,
                    stopGrowth = state.stopGrowth
                ) { onEvent(it) }
            }
        }
    }
}