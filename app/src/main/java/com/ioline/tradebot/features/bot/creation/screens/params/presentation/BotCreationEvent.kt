package com.ioline.tradebot.features.bot.creation.screens.params.presentation

import com.ioline.tradebot.data.models.Instrument
import com.ioline.tradebot.data.repository.instrument.SearchResult

internal sealed class BotCreationEvent {
    sealed class Domain : BotCreationEvent() {
        data class SearchInstrumentResult(
            val searchResult: SearchResult<List<Instrument>>
        ) : Domain()

        data object SearchInstrumentError : Domain()

        data class NextPage(val botId: String) : Domain()
    }

    sealed class Ui : BotCreationEvent() {
        data class ChangeBotName(val name: String) : Ui()
        data class ChangeBotDescription(val description: String) : Ui()
        data class ChangeMarket(val value: String) : Ui()
        data class ChangeMode(val value: String) : Ui()

        object System {
            data object Init : Ui()
        }

        object Click {
            data class SearchInstrument(val text: String) : Ui()
            data object RetrySearchInstrument : Ui()

            data class SelectInstrument(val ticker: String) : Ui()
            data class RemoveInstrument(val ticker: String) : Ui()

            data object Next : Ui()
            data object Close : Ui()
        }
    }
}
