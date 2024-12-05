package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui.params

import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent


@Composable
internal fun CustomParams(
    stopLoss: Int,
    stopGrowth: Int,
    onEvent: (StrategySelectionEvent) -> Unit
) {
    Text("Set Stop Growth: ${stopGrowth}%", fontSize = 18.sp)
    Slider(
        value = stopGrowth.toFloat(),
        onValueChange = { onEvent(StrategySelectionEvent.Ui.Click.ChangeStopGrowth(it)) },
        valueRange = 0f..100f
    )
    Text("Set Stop Loss: ${stopLoss}%", fontSize = 18.sp)
    Slider(
        value = stopLoss.toFloat(),
        onValueChange = { onEvent(StrategySelectionEvent.Ui.Click.ChangeStopLoss(it)) },
        valueRange = 0f..100f
    )
}