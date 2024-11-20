package com.ioline.tradebot.features.bot.creation.screens.params.presentation

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.models.OperationMode
import com.ioline.tradebot.data.repository.instrument.SearchResult
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEvent.Domain
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEvent.Ui
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer
import java.util.UUID
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationCommand as Command
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEffect as Effect
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEvent as Event

internal class BotCreationReducer :
    ScreenDslReducer<Event, Ui, Domain, BotCreationState, Effect, Command>(
        Ui::class, Domain::class
    ) {
    override fun Result.ui(event: Ui) = when (event) {
        Ui.System.Init -> TODO()
        Ui.Click.Next -> effects {
            +Effect.OpenStrategySelection(
                Bot(
                    id = UUID.randomUUID().toString(),
                    name = state.name,
                    description = state.description,
                    isActive = false,
                    instrumentsFIGI = state.selectedInstruments.map { it.figi },
                    marketEnvironment = state.marketEnvironment,
                    mode = state.mode
                )
            )
        }
        is Ui.Click.SearchInstrument -> commands {
            state { copy(searchInstrumentsLoading = true) }
            +Command.SearchInstrument(event.text)
        }
        Ui.Click.RetrySearchInstrument -> TODO()

        is Ui.Click.SelectInstrument -> state {
            copy(
                selectedInstruments = (selectedInstruments +
                        searchInstruments.find { it.ticker == event.ticker })
                    .filterNotNull()
            )
        }
        is Ui.Click.RemoveInstrument -> state {
            copy(
                selectedInstruments = selectedInstruments.filterNot { it.ticker == event.ticker }
            )
        }

        Ui.Click.Close -> effects { +Effect.Close }
        is Ui.ChangeBotName -> state { copy(name = event.name) }
        is Ui.ChangeBotDescription -> state { copy(description = event.description) }
        is Ui.ChangeMarket -> state {
            copy(marketEnvironment = MarketEnvironment.entries.find {
                it.value == event.value
            } ?: MarketEnvironment.HISTORICAL_DATA)
        }
        is Ui.ChangeMode -> state {
            copy(mode = OperationMode.entries.find { it.value == event.value }
                ?: OperationMode.MANUAL)
        }
    }

    override fun Result.internal(event: Domain) = when (event) {
        Domain.SearchInstrumentError -> state { copy(searchInstrumentError = true) }
        is Domain.SearchInstrumentResult -> when (event.searchResult) {
            is SearchResult.Error -> state {
                copy(
                    searchInstrumentsLoading = false,
                    searchInstrumentError = true
                )
            }
            SearchResult.NoResult -> state {
                copy(
                    searchInstrumentsLoading = false,
                    searchInstruments = emptyList()
                )
            }
            is SearchResult.Success -> state {
                copy(
                    searchInstrumentsLoading = false,
                    searchInstruments = event.searchResult.data
                )
            }
        }
    }
}
