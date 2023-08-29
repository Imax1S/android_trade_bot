package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.data.models.strategy.Strategy
import com.ioline.tradebot.data.models.strategy.StrategyType

@Composable
fun StrategySelectionScreen(strategies: List<Strategy>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        strategies.forEach {
            StrategyItem(it)
        }
    }
}

@Preview()
@Composable
fun StrategySelectionScreenPreview() {
    StrategySelectionScreen(strategies = StrategyType.values().map {
        Strategy(it, "", "")
    })
}
