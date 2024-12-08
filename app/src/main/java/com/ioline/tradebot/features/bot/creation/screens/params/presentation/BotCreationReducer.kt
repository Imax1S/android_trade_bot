package com.ioline.tradebot.features.bot.creation.screens.params.presentation

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.models.OperationMode
import com.ioline.tradebot.data.repository.instrument.SearchResult
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEvent.Domain
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEvent.Ui
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer
import java.util.UUID
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationCommand as Command
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEffect as Effect
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEvent as Event

internal class BotCreationReducer :
    ScreenDslReducer<Event, Ui, Domain, BotCreationState, Effect, Command>(
        Ui::class, Domain::class
    ) {
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    private val searchQueryFlow = MutableStateFlow("")

    override fun Result.ui(event: Ui) = when (event) {
        Ui.System.Init -> {
            subSearch()
        }
        Ui.Click.Next -> {
            if (state.name.isNotEmpty() && state.selectedInstruments.isNotEmpty()) {
                commands {
                    +Command.SaveBotLocally(
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
            } else {
                state {
                    copy(
                        errorBotNameValidation = state.name.isEmpty(),
                        errorSelectedTickersValidation = state.selectedInstruments.isEmpty()
                    )
                }
            }
        }
        is Ui.Click.SearchInstrument -> commands {
            if (event.text.isNotEmpty() && event.text.isNotBlank()) {
//                searchQueryFlow.value = event.text
                +Command.SearchInstrument(event.text)
                state { copy(searchInstrumentsLoading = true, searchText = event.text) }
            }
        }
        Ui.Click.RetrySearchInstrument -> commands {
            state { copy(searchInstrumentsLoading = true) }
            +Command.SearchInstrument(state.searchText)
        }

        is Ui.Click.SelectInstrument -> state {
            val newSelectedInstruments = (selectedInstruments + searchInstruments.find {
                it.ticker == event.ticker
            }).filterNotNull()

            copy(selectedInstruments = newSelectedInstruments)
        }
        is Ui.Click.RemoveInstrument -> state {
            copy(
                selectedInstruments = selectedInstruments.filter { it.ticker != event.ticker } + selectedInstruments.filter { it.ticker == event.ticker }
                    .takeLast(selectedInstruments.count { it.ticker == event.ticker })
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

    @OptIn(FlowPreview::class)
    private fun Result.subSearch() {
        searchQueryFlow
            .debounce(3000)
            .distinctUntilChanged()
            .onEach { query ->
                commands {
                    +Command.SearchInstrument(query)
                }
            }.launchIn(coroutineScope)
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
                    searchInstruments = event.searchResult.data ?: emptyList(),
                    searchInstrumentError = event.searchResult.data == null
                )
            }
        }
        is Domain.NextPage -> effects {
            +Effect.OpenStrategySelection(
                event.botId
            )
        }
    }
}
