package com.ioline.tradebot.features.bot.creation.screens.strategy.setup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.ioline.tradebot.common_ui.navigation.NavItem
import com.ioline.tradebot.data.models.strategy.Strategy
import com.ioline.tradebot.data.models.strategy.StrategyType
import com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation.StrategySetupActor
import com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation.StrategySetupEffect
import com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation.StrategySetupReducer
import com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation.StrategySetupState
import com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation.ui.StrategySetupView
import vivid.money.elmslie.coroutines.ElmStoreCompat
import vivid.money.elmslie.coroutines.effects
import vivid.money.elmslie.coroutines.states

@Composable
fun StrategySetupScreen(navigate: (String) -> Unit) {
    val initialState = StrategySetupState(
        strategy = Strategy(
            type = StrategyType.EMA,
            description = "",
            param1 = "dico",
            param2 = "sociis"
        )
    )
    val store = remember {
        ElmStoreCompat(
            initialState,
            StrategySetupReducer,
            StrategySetupActor()
        )
    }

    val state by store.states.collectAsState(
        initialState
    )

    val effect by store.effects.collectAsState(null)

    effect?.let {
        LaunchedEffect(it) {
            when (it) {
                StrategySetupEffect.FinishBotCreation -> navigate(NavItem.HomeScreen.route)
                StrategySetupEffect.OpenPreviousScreen -> TODO()
            }
        }
    }

    StrategySetupView(state) { event ->
        store.accept(event)
    }
}