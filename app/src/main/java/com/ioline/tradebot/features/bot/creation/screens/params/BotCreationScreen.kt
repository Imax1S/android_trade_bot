package com.ioline.tradebot.features.bot.creation.screens.params

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.ioline.tradebot.common_ui.navigation.NavItem
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.models.OperationMode
import com.ioline.tradebot.data.repository.instrument.InstrumentRepository
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationActor
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEffect
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationReducer
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationState
import com.ioline.tradebot.features.bot.creation.screens.params.ui.BotCreationView
import vivid.money.elmslie.coroutines.ElmStoreCompat
import vivid.money.elmslie.coroutines.effects
import vivid.money.elmslie.coroutines.states

@Composable
fun BotCreationScreen(
    instrumentRepository: InstrumentRepository,
    navigate: (String) -> Unit
) {
    val initState = BotCreationState(
        name = "",
        description = "hendrerit",
        marketEnvironment = MarketEnvironment.MARKET,
        mode = OperationMode.MANUAL,
        searchInstruments = listOf(),
        selectedInstruments = listOf(),
        searchInstrumentsLoading = false,
        searchInstrumentError = false
    )

    val store = remember {
        ElmStoreCompat(
            initialState = initState,
            reducer = BotCreationReducer(),
            actor = BotCreationActor(instrumentRepository)
        )
    }

    val state by store.states.collectAsState(
        initState
    )

    val effect by store.effects.collectAsState(null)

    effect?.let {
        LaunchedEffect(it) {
            when (it) {
                BotCreationEffect.Close -> TODO()
                is BotCreationEffect.OpenStrategySelection -> navigate(NavItem.StrategySelection.route)
            }
        }
    }

    BotCreationView(state) { event ->
        store.accept(event)
    }
}