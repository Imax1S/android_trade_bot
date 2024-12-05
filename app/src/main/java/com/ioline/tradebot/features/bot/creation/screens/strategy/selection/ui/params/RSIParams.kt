package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui.params

import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent

@Composable
internal fun RSIParams(
    rsiPeriod: Int,
    onEvent: (StrategySelectionEvent) -> Unit,
    overboughtThreshold: Int,
    oversoldThreshold: Int
) {
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