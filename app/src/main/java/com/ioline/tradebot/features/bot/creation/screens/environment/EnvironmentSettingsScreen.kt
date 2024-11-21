package com.ioline.tradebot.features.bot.creation.screens.environment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.repository.bot.BotRepository
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsActor
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsEffect
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsEvent
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsReducer
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsState
import com.ioline.tradebot.features.bot.creation.screens.environment.ui.EnvironmentSettingsView
import com.ioline.tradebot.navigation.NavItem
import vivid.money.elmslie.coroutines.ElmStoreCompat
import vivid.money.elmslie.coroutines.effects
import vivid.money.elmslie.coroutines.states

@Composable
fun EnvironmentSettingsScreen(
    botId: String,
    botRepository: BotRepository,
    navigate: (NavItem) -> Unit
) {
    val initialState = remember {
        EnvironmentSettingsState(
            type = MarketEnvironment.HISTORICAL_DATA,
            startDate = null,
            endDate = null,
            token = "persecuti"
        )
    }

    val store = remember {
        ElmStoreCompat(
            initialState = initialState,
            reducer = EnvironmentSettingsReducer,
            actor = EnvironmentSettingsActor(botRepository)
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

    LaunchedEffect(null) {
        store.accept(EnvironmentSettingsEvent.Ui.System.Init(botId = botId))
    }

    EnvironmentSettingsView(state) {
        store.accept(it)
    }
}