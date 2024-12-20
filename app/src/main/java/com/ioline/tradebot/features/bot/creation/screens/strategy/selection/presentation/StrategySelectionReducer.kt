package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation

import com.ioline.tradebot.data.generateStockData
import com.ioline.tradebot.data.models.strategy.Strategy
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent.Domain
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent.Ui
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEffect as Effect
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent as Event
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionState as State

internal object StrategySelectionReducer :
    ScreenDslReducer<Event, Ui, Domain, State,
            Effect, StrategySelectionCommand>(Ui::class, Domain::class) {

    override fun Result.ui(event: Ui) = when (event) {
        Ui.System.Init -> {
            state {
                copy(
                    randomData = generateStockData(),
                    maPeriod1 = getDefaultParams().first,
                    maPeriod2 = getDefaultParams().second
                )
            }
        }
        Ui.Click.BackToPreviousScreen -> effects { +Effect.OpenPreviousScreen }
        is Ui.Click.OpenStrategy -> effects { +Effect.Next(state.botId) }
        is Ui.Click.ShowStrategyHint -> effects { +Effect.ShowStrategyHint(event.strategyId) }
        Ui.Click.Next -> {
            commands {
                +StrategySelectionCommand.UpdateBotStrategy(
                    botId = state.botId,
                    strategy = Strategy(
                        type = state.selectedStrategy,
                        param1 = state.maPeriod1.toString(),
                        param2 = state.maPeriod2.toString(),
                    )
                )
            }

        }
        Ui.Click.RegenerateRandomData -> state { copy(randomData = generateStockData()) }
        is Ui.Click.ChangePeriod1Param -> state { copy(maPeriod1 = event.value.toInt()) }
        is Ui.Click.ChangePeriod2Param -> state { copy(maPeriod2 = event.value.toInt()) }
        is Ui.Click.SelectStrategy -> state { copy(selectedStrategy = event.selectedStrategyType) }
        is Ui.Click.ChangeOverboughtThreshold -> state { copy(overboughtThreshold = event.threshold.toInt()) }
        is Ui.Click.ChangeOversoldThreshold -> state { copy(oversoldThreshold = event.threshold.toInt()) }
        is Ui.Click.ChangeRSIPeriod -> state { copy(rsiPeriod = event.period.toInt()) }
        is Ui.Click.ChangeStopGrowth -> state { copy(stopGrowth = event.value.toInt()) }
        is Ui.Click.ChangeStopLoss -> state { copy(stopLoss = event.value.toInt()) }
    }

    override fun Result.internal(event: Domain) = when (event) {
        is Domain.Next -> effects { +Effect.Next(state.botId) }
    }


    private fun getDefaultParams(): Pair<Int, Int> {
        return 12 to 25
    }
}
