package com.ioline.tradebot.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.R
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.features.home.presentation.HomeEvent
import com.ioline.tradebot.features.home.presentation.HomeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeView(state: HomeState, onEvent: (HomeEvent) -> Unit) {
    Scaffold(
        floatingActionButton = {
            if (!state.isError) {
                FloatingActionButton(onClick = {
                    onEvent(HomeEvent.Ui.CreateNewBotClick)
                }) {
                    Icon(Icons.Default.Add, stringResource(R.string.add_a_bot))
                }
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
                            "account",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                },
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when {
                state.isError -> {
                    item {
                        HomeError(onEvent)
                    }
                }
                state.isLoading -> {
                    item {
                        HomeLoading()
                    }
                }
                state.data.isEmpty() -> {
                    item {
                        Text(
                            text = LocalContext.current.getString(R.string.home_empty),
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace
                            ),
                        )
                    }
                }
                else -> {
                    items(state.data) { bot ->
                        Spacer(Modifier.size(8.dp))
                        BotItem(bot, onEvent)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeView(
        HomeState(
            data = mutableListOf()
        )
    ) {}
}

@Preview
@Composable
fun HomeScreenWithBotsPreview() {
    HomeView(
        HomeState(
            data = MutableList(5) {
                Bot(
                    name = "Bot$it",
                    marketEnvironment = MarketEnvironment.MARKET
                )
            }
        )
    ) {}
}

@Preview
@Composable
fun HomeScreenErrorPreview() {
    HomeView(
        HomeState(
            data = mutableListOf(),
            isError = true
        )
    ) {}
}

@Preview
@Composable
private fun HomeScreenLoadingPreview() {
    HomeView(
        HomeState(
            data = mutableListOf(),
            isLoading = true
        )
    ) {}
}