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
    MAPeriod1: Int,
    MAPeriod2: Int,
    rsiPeriod: Int,
    oversoldThreshold: Int,
    overboughtThreshold: Int,
    onEvent: (StrategySelectionEvent) -> Unit
) {
    when (selectedStrategy) {
        StrategyType.EMA, StrategyType.MA -> {
            Text("Period: $MAPeriod1", fontSize = 18.sp)
            Slider(
                value = MAPeriod1.toFloat(),
                onValueChange = { onEvent(StrategySelectionEvent.Ui.Click.ChangePeriod1Param(it)) },
                valueRange = 9f..26f
            )

            Text("Period: $MAPeriod2", fontSize = 18.sp)
            Slider(
                value = MAPeriod2.toFloat(),
                onValueChange = { onEvent(StrategySelectionEvent.Ui.Click.ChangePeriod2Param(it)) },
                valueRange = 26f..50f
            )
        }
        StrategyType.RSI -> {
            Text("RSI Period: $rsiPeriod", fontSize = 18.sp)
            Slider(
                value = rsiPeriod.toFloat(),
                onValueChange = { onEvent(StrategySelectionEvent.Ui.Click.ChangeRSIPeriod(it)) },
                valueRange = 1f..50f
            )

            Text("Overbought Threshold: $overboughtThreshold", fontSize = 18.sp)
            Slider(
                value = overboughtThreshold.toFloat(),
                onValueChange = {
                    onEvent(
                        StrategySelectionEvent.Ui.Click.ChangeOverboughtThreshold(
                            it
                        )
                    )
                },
                valueRange = 50f..100f
            )

            Text("Oversold Threshold: $oversoldThreshold", fontSize = 18.sp)
            Slider(
                value = oversoldThreshold.toFloat(),
                onValueChange = {
                    onEvent(
                        StrategySelectionEvent.Ui.Click.ChangeOversoldThreshold(
                            it
                        )
                    )
                },
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