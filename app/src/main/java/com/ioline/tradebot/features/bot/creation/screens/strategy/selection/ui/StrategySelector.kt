package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.data.models.strategy.StrategyType
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionState

@Composable
internal fun StrategySelector(
    state: StrategySelectionState,
    selectedStrategy: StrategyType,
    onEvent: (StrategySelectionEvent) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        state.strategies.forEach { strategy ->
            Button(
                onClick = { onEvent(StrategySelectionEvent.Ui.Click.SelectStrategy(strategy.type)) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedStrategy == strategy.type) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    }
                )
            ) {
                Text(strategy.type.name, maxLines = 1)
            }
        }
    }
}