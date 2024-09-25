package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation

import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent.Domain
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent.Ui
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEffect as Effect
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object StrategySelectionReducer : ScreenDslReducer<StrategySelectionEvent, Ui, Domain, StrategySelectionState,
        Effect, StrategySelectionCommand>(Ui::class, Domain::class) {

    override fun Result.ui(event: Ui) = when (event) {
        Ui.System.Init -> {
            //todo load strategies (optional)
        }
        Ui.Click.BackToPreviousScreen -> effects { +Effect.OpenPreviousScreen }
        is Ui.Click.OpenStrategy -> effects { +Effect.OpenStrategy(state.bot, event.strategyId) }
        is Ui.Click.ShowStrategyHint -> effects { +Effect.ShowStrategyHint(event.strategyId) }
    }

    override fun Result.internal(event: Domain) = when (event) {
        else -> TODO("Not yet implemented")
    }
}
