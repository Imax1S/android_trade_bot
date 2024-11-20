package com.ioline.tradebot.features.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.repository.bot.BotRepository
import com.ioline.tradebot.features.home.presentation.HomeActor
import com.ioline.tradebot.features.home.presentation.HomeEffect
import com.ioline.tradebot.features.home.presentation.HomeEvent
import com.ioline.tradebot.features.home.presentation.HomeReducer
import com.ioline.tradebot.features.home.presentation.HomeState
import com.ioline.tradebot.features.home.ui.HomeView
import com.ioline.tradebot.navigation.NavItem
import vivid.money.elmslie.coroutines.ElmStoreCompat
import vivid.money.elmslie.coroutines.effects
import vivid.money.elmslie.coroutines.states

@Composable
fun HomeScreen(botRepository: BotRepository, navigate: (NavItem) -> Unit) {
    val initialState = HomeState(
        data = mutableListOf(
            Bot(
                id = "eleifend",
                name = "Lyle Wiggins",
                strategy = null,
                isActive = false,
                instrumentsFIGI = listOf(),
                marketEnvironment = MarketEnvironment.MARKET,
                timeSettings = null,
                result = null
            )
        ),
        isError = false,
        isLoading = true
    )
    val store = remember {
        ElmStoreCompat(
            initialState = initialState,
            reducer = HomeReducer(),
            actor = HomeActor(botRepository)
        )
    }

    val state by store.states.collectAsState(
        initialState
    )

    val effect by store.effects.collectAsState(null)

    effect?.let {
        LaunchedEffect(it) {
            when (it) {
                HomeEffect.NavigateToAccount -> navigate(NavItem.Settings)
                is HomeEffect.NavigateToBot -> navigate(NavItem.BotReview)
                HomeEffect.NavigateToBotCreation -> navigate(NavItem.BotCreation)
            }
        }
    }

    LaunchedEffect(Unit) {
        store.accept(HomeEvent.Ui.Init)
    }

    HomeView(state) {
        store.accept(it)
    }
}