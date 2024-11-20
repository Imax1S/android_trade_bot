package com.ioline.tradebot.features.bot.overview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.HistoricalResult
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.features.bot.overview.presenation.BotOverviewActor
import com.ioline.tradebot.features.bot.overview.presenation.BotOverviewEvent
import com.ioline.tradebot.features.bot.overview.presenation.BotOverviewReducer
import com.ioline.tradebot.features.bot.overview.presenation.BotReviewEffect
import com.ioline.tradebot.features.bot.overview.presenation.BotReviewState
import com.ioline.tradebot.features.bot.overview.ui.BotOverviewView
import com.ioline.tradebot.mockInstruments
import com.ioline.tradebot.mockNumbers
import com.ioline.tradebot.mockOperations
import com.ioline.tradebot.navigation.NavItem
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
            result = HistoricalResult(
                finalBalance = 36.37, yield = 38.39, history = mockNumbers
            ),
            operations = mockOperations,
            assets = mockInstruments
        )
    )
    val store = remember {
        ElmStoreCompat(
            initialState,
            BotOverviewReducer,
            BotOverviewActor()
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
        store.accept(BotOverviewEvent.Ui.System.Init(initialState.bot))
    }

    BotOverviewView(state) { event ->
        store.accept(event)
    }
}