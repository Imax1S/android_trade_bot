package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation

import com.ioline.tradebot.data.models.strategy.Strategy
import com.ioline.tradebot.data.models.strategy.StrategyType
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent.Domain
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent.Ui
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer
import kotlin.random.Random
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
                    MAPeriod1 = getDefaultParams(state.selectedStrategy).first,
                    MAPeriod2 = getDefaultParams(state.selectedStrategy).second
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
                        param1 = state.MAPeriod1.toString(),
                        param2 = state.MAPeriod2.toString(),
                    )
                )
            }

        }
        Ui.Click.RegenerateRandomData -> state { copy(randomData = generateStockData()) }
        is Ui.Click.ChangePeriod1Param -> state { copy(MAPeriod1 = event.value) }
        is Ui.Click.ChangePeriod2Param -> state { copy(MAPeriod2 = event.value) }
        is Ui.Click.SelectStrategy -> state { copy(selectedStrategy = event.selectedStrategyType) }
    }

    override fun Result.internal(event: Domain) = when (event) {
        is Domain.Next -> effects { +Effect.Next(state.botId) }
    }

    private fun generateStockData(
        points: Int = 365,
        startPrice: Int = 0,
        volatility: Double = 30.0,
        trend: Double = 0.1
    ): List<Int> {
        val prices = mutableListOf(startPrice)
        val random = Random(System.currentTimeMillis())

        for (i in 1 until points) {
            val previousPrice = prices.last()
            val randomShock = (random.nextDouble() - 0.5) * volatility
            val newPrice = previousPrice + randomShock + trend
            prices.add(newPrice.coerceIn(10.0, 200.0).toInt())
        }

        return prices
    }

    private fun getDefaultParams(selectedStrategy: StrategyType): Pair<Float, Float> {
        return 12f to 25f
    }
}
