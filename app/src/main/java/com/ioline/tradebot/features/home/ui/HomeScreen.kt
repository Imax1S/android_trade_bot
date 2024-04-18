package com.ioline.tradebot.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.R
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.features.home.presentation.homescreen.HomeEvent
import com.ioline.tradebot.features.home.presentation.homescreen.HomeState

@Composable
internal fun HomeScreen(state: HomeState, onCreateBotClick: (HomeEvent) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (state.data.isEmpty()) {
            Button(onClick = {
                onCreateBotClick(HomeEvent.Ui.CreateNewBotClick)
            }) {
                Text(text = LocalContext.current.getString(R.string.home_empty))
            }
            return@Column
        } else {
            state.data.forEach {
                Spacer(Modifier.size(8.dp))
                BotItem(it)
            }
        }
    }
}

@Composable
fun BotItem(bot: Bot) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),

        ) {
        Row {
            Text(
                text = bot.name,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace
                ),
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(modifier = Modifier.padding(end = 16.dp), checked = false, onCheckedChange = {})
        }

        Text(
            text = bot.name,
            style = TextStyle(
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = bot.name,
            style = TextStyle(
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = bot.name,
            style = TextStyle(
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        HomeState(
            data = emptyList()
        )
    ) {
    }
}

@Preview
@Composable
fun HomeScreenWithBotsPreview() {
    HomeScreen(
        HomeState(
            data = listOf(
                Bot(
                    name = "Bot1",
                    marketEnvironment = MarketEnvironment.MARKET
                ),
                Bot(
                    name = "Bot1",
                    marketEnvironment = MarketEnvironment.MARKET
                ),
                Bot(
                    name = "Bot1",
                    marketEnvironment = MarketEnvironment.MARKET
                )
            )
        )
    ) {

    }
}