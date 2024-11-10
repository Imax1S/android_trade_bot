package com.ioline.tradebot.features.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.ioline.tradebot.common_ui.navigation.NavItem
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.repository.bot.BotRepository
import com.ioline.tradebot.features.home.presentation.homescreen.HomeActor
import com.ioline.tradebot.features.home.presentation.homescreen.HomeEffect
import com.ioline.tradebot.features.home.presentation.homescreen.HomeReducer
import com.ioline.tradebot.features.home.presentation.homescreen.HomeState
import vivid.money.elmslie.coroutines.ElmStoreCompat
import vivid.money.elmslie.coroutines.effects
import vivid.money.elmslie.coroutines.states

@Composable
fun HomeScreen(botRepository: BotRepository, navigate: (String) -> Unit) {
    val initialState = HomeState(
        data = listOf(
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
        isLoading = false
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
                HomeEffect.NavigateToAccount -> navigate(NavItem.Settings.route)
                is HomeEffect.NavigateToBot -> navigate(NavItem.BotReview.route)
                HomeEffect.NavigateToBotCreation -> navigate(NavItem.BotCreation.route)
            }
        }
    }

    HomeView(state) {
        store.accept(it)
    }
}