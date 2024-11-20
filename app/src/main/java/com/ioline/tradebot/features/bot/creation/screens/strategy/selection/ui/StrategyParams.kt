package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui

import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import com.ioline.tradebot.data.models.strategy.StrategyType
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent

@Composable
internal fun StrategyParams(
    selectedStrategy: StrategyType,
    period1: Float,
    period2: Float,
    oversoldThreshold: Int,
    overboughtThreshold: Int,
    onEvent: (StrategySelectionEvent) -> Unit
) {
    var oversoldThreshold1 = oversoldThreshold
    var overboughtThreshold1 = overboughtThreshold
    when (selectedStrategy) {
        StrategyType.EMA, StrategyType.MA -> {
            Text("Period: ${period1.toInt()}", fontSize = 18.sp)
            Slider(
                value = period1,
                onValueChange = { onEvent(StrategySelectionEvent.Ui.Click.ChangePeriod1Param(it)) },
                valueRange = 9f..26f
            )

            Text("Period: ${period2.toInt()}", fontSize = 18.sp)
            Slider(
                value = period2,
                onValueChange = { onEvent(StrategySelectionEvent.Ui.Click.ChangePeriod2Param(it)) },
                valueRange = 26f..50f
            )
        }
        StrategyType.RSI -> {
            Text("RSI Period: ${period1.toInt()}", fontSize = 18.sp)
            Slider(
                value = oversoldThreshold1.toFloat(),
                onValueChange = { onEvent(StrategySelectionEvent.Ui.Click.ChangePeriod1Param(it)) },
                valueRange = 1f..100f
            )

            Text("Overbought Threshold: $overboughtThreshold1", fontSize = 18.sp)
            Slider(
                value = overboughtThreshold1.toFloat(),
                onValueChange = { overboughtThreshold1 = it.toInt() },
                valueRange = 50f..100f
            )

            Text("Oversold Threshold: $oversoldThreshold1", fontSize = 18.sp)
            Slider(
                value = oversoldThreshold1.toFloat(),
                onValueChange = { oversoldThreshold1 = it.toInt() },
                valueRange = 0f..50f
            )
        }
        StrategyType.CUSTOM -> {
            var stopLoss by remember { mutableFloatStateOf(0f) }
            var stopGrowth by remember { mutableFloatStateOf(0f) }

            Text("Set Stop Loss: ${stopLoss.toInt()}%", fontSize = 18.sp)
            Slider(
                value = stopLoss,
                onValueChange = { stopLoss = it },
                valueRange = 0f..100f
            )

            Text("Set Stop Growth: ${stopGrowth.toInt()}%", fontSize = 18.sp)
            Slider(
                value = stopGrowth,
                onValueChange = { stopGrowth = it },
                valueRange = 0f..100f
            )
        }
    }
}