package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui.params

import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.ioline.tradebot.data.models.strategy.StrategyType
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent

@Composable
internal fun StrategyParams(
    selectedStrategy: StrategyType,
    maPeriod1: Int,
    maPeriod2: Int,
    rsiPeriod: Int,
    oversoldThreshold: Int,
    overboughtThreshold: Int,
    stopLoss: Int,
    stopGrowth: Int,
    onEvent: (StrategySelectionEvent) -> Unit
) {
    when (selectedStrategy) {
        StrategyType.EMA, StrategyType.MA -> {
            Text("Period: $maPeriod1", fontSize = 18.sp)
            Slider(
                value = maPeriod1.toFloat(),
                onValueChange = { onEvent(StrategySelectionEvent.Ui.Click.ChangePeriod1Param(it)) },
                valueRange = 9f..26f
            )

            Text("Period: $maPeriod2", fontSize = 18.sp)
            Slider(
                value = maPeriod2.toFloat(),
                onValueChange = { onEvent(StrategySelectionEvent.Ui.Click.ChangePeriod2Param(it)) },
                valueRange = 26f..50f
            )
        }
        StrategyType.RSI -> {
            RSIParams(rsiPeriod, onEvent, overboughtThreshold, oversoldThreshold)
        }
        StrategyType.CUSTOM -> {
            CustomParams(stopLoss, stopGrowth, onEvent)
        }
    }
}