package com.ioline.tradebot.features.bot.review

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.ioline.tradebot.common_ui.navigation.NavItem
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.features.bot.review.presenation.BotReviewActor
import com.ioline.tradebot.features.bot.review.presenation.BotReviewEffect
import com.ioline.tradebot.features.bot.review.presenation.BotReviewReducer
import com.ioline.tradebot.features.bot.review.presenation.BotReviewState
import com.ioline.tradebot.features.bot.review.ui.BotReviewView
import vivid.money.elmslie.coroutines.ElmStoreCompat
import vivid.money.elmslie.coroutines.effects
import vivid.money.elmslie.coroutines.states

@Composable
fun BotReviewScreen(navigateTo: (NavItem) -> Unit) {
    val initialState = BotReviewState(
        bot = Bot(
            id = "cum",
            name = "Fanny Hoffman",
            strategy = null,
            isActive = false,
            instrumentsFIGI = listOf(),
            marketEnvironment = MarketEnvironment.MARKET,
            timeSettings = null,
            result = null
        )
    )
    val store = remember {
        ElmStoreCompat(
            initialState,
            BotReviewReducer,
            BotReviewActor()
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

    BotReviewView(state) { event ->
        store.accept(event)
    }
}