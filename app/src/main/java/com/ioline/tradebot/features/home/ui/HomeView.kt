package com.ioline.tradebot.features.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeView(state: HomeState, onEvent: (HomeEvent) -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(HomeEvent.Ui.CreateNewBotClick)
            }) {
                Icon(Icons.Default.Add, "Add a bot")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(onClick = { onEvent(HomeEvent.Ui.OpenAccount) }) {
                        Icon(
                            Icons.Default.AccountCircle,
                            "Hamster icon",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state.data.isEmpty()) {
                Text(
                    text = LocalContext.current.getString(R.string.home_empty),
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace
                    ),
                )
            } else {
                state.data.forEach {
                    Spacer(Modifier.size(8.dp))
                    BotItem(it, onEvent)
                }
            }
        }
    }
}

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
                    text = "Richest king",
                    style = MaterialTheme.typography.headlineLarge,
                )

                Spacer(Modifier.weight(1f))
                Switch(
                    checked = true, // Можно установить значение по умолчанию
                    onCheckedChange = { /* Handle toggle change */ },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF6A4FA0)
                    )
                )
            }


            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "-50%",
                color = Color(0xFF6A4FA0),
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$.APPL • $.YNDX • $.GOOG • $.MTA",
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Bot with strategy",
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeView(
        HomeState(
            data = emptyList()
        )
    ) {
    }
}

@Preview
@Composable
fun HomeScreenWithBotsPreview() {
    HomeView(
        HomeState(
            data = List(5) {
                Bot(
                    name = "Bot$it",
                    marketEnvironment = MarketEnvironment.MARKET
                )
            }
        )
    ) {

    }
}