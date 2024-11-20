package com.ioline.tradebot.features.bot.overview.presenation

import com.ioline.tradebot.features.bot.overview.presenation.BotOverviewEvent.Domain
import com.ioline.tradebot.features.bot.overview.presenation.BotOverviewEvent.Ui
import com.ioline.tradebot.features.bot.overview.presenation.ChartPeriod.ALL
import com.ioline.tradebot.features.bot.overview.presenation.ChartPeriod.DAY
import com.ioline.tradebot.features.bot.overview.presenation.ChartPeriod.MONTH
import com.ioline.tradebot.features.bot.overview.presenation.ChartPeriod.SIX_MONTHS
import com.ioline.tradebot.features.bot.overview.presenation.ChartPeriod.WEEK
import com.ioline.tradebot.features.bot.overview.presenation.ChartPeriod.YEAR
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer
import com.ioline.tradebot.features.bot.overview.presenation.BotOverviewEvent as Event
import com.ioline.tradebot.features.bot.overview.presenation.BotReviewCommand as Command
import com.ioline.tradebot.features.bot.overview.presenation.BotReviewEffect as Effect
import com.ioline.tradebot.features.bot.overview.presenation.BotReviewState as State

internal object BotOverviewReducer :
    ScreenDslReducer<Event, Ui, Domain, State, Effect, Command>(Ui::class, Domain::class) {

    override fun Result.ui(event: Ui) = when (event) {
        is Ui.System.Init -> {
            state {
                copy()
            }
            commands {
                +Command.Init(event.botId)
            }
        }

//            state {
//            copy(
//                dataForSelectedPeriod = getDataForSelectedPeriod(
//                    state.bot.result?.history ?: emptyList(), state.selectedPeriod
//                ),
//            )
//        }
        Ui.Click.Back -> effects {
            +Effect.Close
        }
        is Ui.Click.InteractWithChar -> TODO()
        is Ui.Click.OpenStrategy -> effects {
            +Effect.OpenStrategy
        }
        is Ui.Click.SelectChartPeriod -> state {
            copy(
                selectedPeriod = event.period,
                dataForSelectedPeriod = getDataForSelectedPeriod(
                    state.bot?.result?.history ?: emptyList(), event.period
                )
            )
        }
        Ui.Click.MakeHistoricalLaunch -> TODO()
    }

    override fun Result.internal(event: Domain) = when (event) {
        is Domain.Error -> state {
            copy(bot = null)
        }
        is Domain.LoadData -> state {
            copy(bot = event.bot)
        }
        is Domain.UpdateBot -> state {
            copy(bot = event.bot)
        }
    }

    private fun getDataForSelectedPeriod(
        data: List<Double>,
        selectedChartPeriod: ChartPeriod
    ): List<Double> {
        return when (selectedChartPeriod) {
            ALL -> data
            YEAR -> data.takeLast(30)
            SIX_MONTHS -> data.takeLast(20)
            MONTH -> data.takeLast(10)
            WEEK -> data.takeLast(5)
            DAY -> data.takeLast(3)
        }
    }
}