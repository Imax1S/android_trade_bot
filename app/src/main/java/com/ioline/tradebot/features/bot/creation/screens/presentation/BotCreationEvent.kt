package com.ioline.tradebot.features.bot.creation.screens.presentation

import com.ioline.tradebot.data.models.Instrument
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.models.OperationMode
import com.ioline.tradebot.data.repository.instrument.SearchResult

internal sealed class BotCreationEvent {
    sealed class Domain : BotCreationEvent() {
        data class SearchInstrumentResult(val searchResult: SearchResult<List<Instrument>>) : Domain()
        object SearchInstrumentError : Domain()
    }

    sealed class Ui : BotCreationEvent() {
        object System {
            object Init : Ui()
        }

        data class SetName(val text: String) : Ui()
        data class SetDescription(val text: String) : Ui()

        object Click {
            data class SetMode(val mode: OperationMode) : Ui()
            data class SetEnvironment(val environment: MarketEnvironment) : Ui()
            data class SearchInstrument(val text: String) : Ui()
            data class SelectInstrument(val figi: String) : Ui()
            data class RemoveInstrument(val figi: String) : Ui()
            object Next : Ui()
            object Close : Ui()
        }

        object Action {
            // your code
        }
    }
}
