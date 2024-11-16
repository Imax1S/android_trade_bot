package com.ioline.tradebot.features.bot.creation.screens.environment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.ioline.tradebot.common_ui.navigation.NavItem
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.models.OperationMode
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsActor
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsEffect
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsReducer
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsState
import com.ioline.tradebot.features.bot.creation.screens.environment.ui.EnvironmentSettingsView
import vivid.money.elmslie.coroutines.ElmStoreCompat
import vivid.money.elmslie.coroutines.effects
import vivid.money.elmslie.coroutines.states

@Composable
fun EnvironmentSettingsScreen(navigate: (NavItem) -> Unit) {
    val initialState = remember {
        EnvironmentSettingsState(
            type = MarketEnvironment.HISTORICAL_DATA, bot = Bot(
                id = "dolorum",
                name = "Eve O'Neil",
                description = "oratio",
                strategy = null,
                isActive = false,
                instrumentsFIGI = listOf(),
                marketEnvironment = MarketEnvironment.HISTORICAL_DATA,
                timeSettings = null,
                mode = OperationMode.MANUAL,
                result = null
            ), startDate = null,
            endDate = null,
            token = "persecuti"

        )
    }

    val store = remember {
        ElmStoreCompat(
            initialState = initialState,
            reducer = EnvironmentSettingsReducer,
            actor = EnvironmentSettingsActor()
        )
    }

    val state by store.states.collectAsState(
        initialState
    )

    val effect by store.effects.collectAsState(null)

    effect?.let {
        LaunchedEffect(it) {
            when (it) {
                EnvironmentSettingsEffect.Back -> TODO()
                EnvironmentSettingsEffect.NavigateToHome -> navigate(NavItem.HomeScreen)
                EnvironmentSettingsEffect.ShowInstruction -> TODO()
            }
        }
    }

    EnvironmentSettingsView(state) {
        store.accept(it)
    }
}