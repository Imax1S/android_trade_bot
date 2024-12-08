package com.ioline.tradebot.features.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.HistoricalResult
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.features.home.presentation.HomeEvent

@Composable
internal fun BotItem(bot: Bot, onEvent: (HomeEvent) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .wrapContentHeight()
            .clickable { onEvent(HomeEvent.Ui.OpenBot(bot.id)) },

        ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    text = bot.name,
                    style = MaterialTheme.typography.headlineLarge,
                )

                Spacer(Modifier.weight(1f))
                Switch(
                    checked = bot.isActive,
                    onCheckedChange = { value ->
                        onEvent(
                            HomeEvent.Ui.SwitchBotModeClick(
                                bot.id,
                                value
                            )
                        )
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF6A4FA0)
                    )
                )
            }


            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${bot.result?.yield ?: 0.0}%",
                color = if ((bot.result?.yield ?: 0.0) >= 0.0) {
                    Color(0xFF77DD77)
                } else {
                    Color(0xFFFF6961)
                },
                style = MaterialTheme.typography.headlineMedium,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = bot.instrumentsFIGI.joinToString(" • "),
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = bot.description,
            )
        }
    }
}

@Preview
@Composable
private fun BotItemPreview() {
    BotItem(
        bot = Bot(
            id = "bot_id",
            name = "Richest bot",
            description = "Bot is super cool and uses super cool strategy",
            strategy = null,
            isActive = false,
            instrumentsFIGI = listOf(
                "APPL",
                "YNDX",
                "NTFX",
            ),
            marketEnvironment = MarketEnvironment.MARKET,
            timeSettings = null,
            result = HistoricalResult(
                finalBalance = 4.5, yield = 6.7
            )
        )
    ) { }
}