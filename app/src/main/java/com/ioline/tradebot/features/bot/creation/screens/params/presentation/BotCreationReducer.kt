package com.ioline.tradebot.features.bot.creation.screens.params.presentation

import com.ioline.tradebot.data.repository.instrument.SearchResult
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEvent.Domain
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEvent.Ui
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationCommand as Command
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEffect as Effect
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEvent as Event

internal object BotCreationReducer :
    ScreenDslReducer<Event, Ui, Domain, BotCreationState, Effect, Command>(
        Ui::class, Domain::class
    ) {
    override fun Result.ui(event: Ui) = when (event) {
        Ui.System.Init -> TODO()
        Ui.Click.Next -> effects { +Effect.OpenStrategySelection }
        is Ui.Click.SearchInstrument -> commands {
            +Command.SearchInstrument(event.text)
        }

        is Ui.Click.SelectInstrument -> state {
            copy(
                selectedInstruments = (selectedInstruments +
                        searchInstruments.find { it.figi == event.figi })
                    .filterNotNull()
            )
        }

        is Ui.Click.RemoveInstrument -> state {
            copy(
                selectedInstruments = selectedInstruments.filterNot { it.figi == event.figi }
            )
        }

        is Ui.SetDescription -> state { copy(description = event.text) }
        is Ui.Click.SetEnvironment -> state { copy(marketEnvironment = event.environment) }
        is Ui.Click.SetMode -> state { copy(mode = event.mode) }
        is Ui.SetName -> state { copy(name = event.text) }
        Ui.Click.Close -> effects { +Effect.Close }
    }

    override fun Result.internal(event: Domain) = when (event) {
        Domain.SearchInstrumentError -> state { copy(searchInstrumentError = true) }
        is Domain.SearchInstrumentResult -> when (event.searchResult) {
            is SearchResult.Error -> state { copy(searchInstrumentError = true) }
            SearchResult.NoResult -> state { copy(searchInstruments = emptyList()) }
            is SearchResult.Success -> state { copy(searchInstruments = event.searchResult.data) }
        }
    }
}
