package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.data.models.Strategy
import com.ioline.tradebot.data.models.StrategyType

@Composable
fun StrategySelectionScreen(strategies: List<Strategy>) {
    Column {
        strategies.forEach {
            StrategyItem(it)
        }
    }
}

@Composable
fun StrategyItem(strategy: Strategy) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row( verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)) {
                Text(
                    text = strategy.type.name,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace
                    )
                )
                Text(
                    text = "Description",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Default.ArrowForward,
                    null
                )
            }
        }
    }
}

@Preview
@Composable
fun StrategyItemPreview() {
    StrategyItem(
        Strategy(StrategyType.EMA, "", "")
    )
}
