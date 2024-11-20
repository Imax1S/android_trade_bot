package com.ioline.tradebot.features.bot.overview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.ioline.tradebot.data.repository.bot.BotRepository
import com.ioline.tradebot.features.bot.overview.presenation.BotOverviewActor
import com.ioline.tradebot.features.bot.overview.presenation.BotOverviewEvent
import com.ioline.tradebot.features.bot.overview.presenation.BotOverviewReducer
import com.ioline.tradebot.features.bot.overview.presenation.BotReviewEffect
import com.ioline.tradebot.features.bot.overview.presenation.BotReviewState
import com.ioline.tradebot.features.bot.overview.ui.BotOverviewView
import com.ioline.tradebot.navigation.NavItem
import vivid.money.elmslie.coroutines.ElmStoreCompat
import vivid.money.elmslie.coroutines.effects
import vivid.money.elmslie.coroutines.states

@Composable
fun BotReviewScreen(botId: String, botRepository: BotRepository, navigateTo: (NavItem) -> Unit) {
    val initialState = BotReviewState()
    val store = remember {
        ElmStoreCompat(
            initialState,
            BotOverviewReducer,
            BotOverviewActor(botRepository)
        )
    }

    val state by store.states.collectAsState(
        initialState
    )

    val effect by store.effects.collectAsState(null)

    effect?.let {
        LaunchedEffect(it) {
            when (it) {
                BotReviewEffect.Close -> navigateTo(NavItem.HomeScreen)
                BotReviewEffect.OpenStrategy -> navigateTo(NavItem.StrategySettings)
            }
        }
    }

    LaunchedEffect(Unit) {
        store.accept(BotOverviewEvent.Ui.System.Init(botId))
    }

    BotOverviewView(state) { event ->
        store.accept(event)
    }
}