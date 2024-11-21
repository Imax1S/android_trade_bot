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
                copy(operations = bot?.operations ?: emptyList())
            }
            commands {
                +Command.Init(event.botId)
            }
        }
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
                    state.runData,
                    event.period
                )
            )
        }
        Ui.Click.MakeHistoricalLaunch -> {
            commands { +state.bot?.id?.let { Command.RunBotOnHistoricalData(it) } }
        }
        Ui.Click.EnterToEditMode -> state { copy(inEditMode = true) }
        Ui.Click.SaveChanges -> state { copy(inEditMode = false) }
        is Ui.Click.ChangeBotDescription -> {
            val updatedBot = state.bot?.copy(description = event.description)
            state { copy(bot = updatedBot) }
        }
        is Ui.Click.ChangeBotName -> {
            val updatedBot = state.bot?.copy(name = event.name)
            state { copy(bot = updatedBot) }
        }
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
        is Domain.LoadRunResult -> state {
            copy(
                runData = event.historicalResult?.history ?: emptyList(),
                dataForSelectedPeriod = event.historicalResult?.history ?: emptyList(),
                yield = event.historicalResult?.yield ?: 0.0,
                operations = event.historicalResult?.operations ?: emptyList()
            )
        }
    }

    private fun getDataForSelectedPeriod(
        data: List<Double>,
        selectedChartPeriod: ChartPeriod
    ): List<Double> {
        return when (selectedChartPeriod) {
            ALL -> data
            YEAR -> data.takeLast(360)
            SIX_MONTHS -> data.takeLast(180)
            MONTH -> data.takeLast(30)
            WEEK -> data.takeLast(14)
            DAY -> data.takeLast(12)
        }
    }
}