package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent

@Composable
internal fun YieldText(profit: Double, onEvent: (StrategySelectionEvent) -> Unit) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Demo on random data", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.size(8.dp))
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Reload",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onEvent(StrategySelectionEvent.Ui.Click.RegenerateRandomData) }
            )
        }
        Text(
            text = "Profit: ${if (profit > 0) "+%.2f".format(profit * 100) else "%.2f".format(profit * 100)}%",
            style = MaterialTheme.typography.bodyMedium,
            color = if (profit > 0.0) {
                Color(0xFF1C8F22)
            } else {
                Color(0xFFE91E63)
            }
        )
    }
}