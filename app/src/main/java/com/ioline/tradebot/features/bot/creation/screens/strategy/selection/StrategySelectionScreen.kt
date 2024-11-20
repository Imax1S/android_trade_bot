package com.ioline.tradebot.features.bot.creation.screens.strategy.selection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.ioline.tradebot.data.models.strategy.Strategy
import com.ioline.tradebot.data.models.strategy.StrategyType
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionActor
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionReducer
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionState
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui.StrategySelectionView
import com.ioline.tradebot.navigation.NavItem
import vivid.money.elmslie.coroutines.ElmStoreCompat
import vivid.money.elmslie.coroutines.effects
import vivid.money.elmslie.coroutines.states
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEffect as Effect
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent as Event

@Composable
fun StrategySelectionScreen(botId: String, navigateTo: (String) -> Unit) {
    val initialState = StrategySelectionState(
        botId = botId,
        strategies = StrategyType.entries.map {
            Strategy(it, "", "", "")
        }
    )

    val store = remember {
        ElmStoreCompat(
            initialState = initialState,
            reducer = StrategySelectionReducer,
            actor = StrategySelectionActor()
        )
    }

    val state by store.states.collectAsState(
        initialState
    )

    val effect by store.effects.collectAsState(null)

    effect?.let {
        LaunchedEffect(it) {
            when (it) {
                Effect.OpenPreviousScreen -> TODO()
                is Effect.Next -> navigateTo(NavItem.EnvironmentSettings.route)
                is Effect.ShowStrategyHint -> TODO()
            }
        }
    }

    LaunchedEffect(null) {
        store.accept(Event.Ui.System.Init)
    }

    StrategySelectionView(state) { event ->
        store.accept(event)
    }
}

@Preview
@Composable
private fun StrategySelectionScreenPreview() {
    StrategySelectionScreen("botId") { }
}