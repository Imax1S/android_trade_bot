package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.data.models.strategy.Strategy
import com.ioline.tradebot.data.models.strategy.StrategyType
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent

@Composable
internal fun StrategySelectionView(
    strategies: List<Strategy>,
    onEvent: (StrategySelectionEvent) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize()) {
        strategies.forEach {
            StrategyItem(it, onEvent)
        }
    }
}

@Preview()
@Composable
fun StrategySelectionScreenPreview() {
    StrategySelectionView(strategies = StrategyType.entries.map {
        Strategy(it, "", "")
    }, onEvent = {})
}
