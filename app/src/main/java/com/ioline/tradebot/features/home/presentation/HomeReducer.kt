package com.ioline.tradebot.features.home.presentation

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.features.home.presentation.HomeEvent
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer
import com.ioline.tradebot.features.home.presentation.HomeCommand as Command
import com.ioline.tradebot.features.home.presentation.HomeEffect as Effect
import com.ioline.tradebot.features.home.presentation.HomeEvent as Event

internal class HomeReducer :
    ScreenDslReducer<Event, Event.Ui, Event.Domain, HomeState, Effect, Command>(
        Event.Ui::class,
        Event.Domain::class
    ) {
    override fun Result.ui(event: Event.Ui) {
        when (event) {
            Event.Ui.Init -> commands { +Command.LoadData }
            Event.Ui.ReloadClick -> {
                state { copy(isLoading = true) }
                commands { +Command.LoadData }
            }
            Event.Ui.CreateNewBotClick -> effects { +Effect.NavigateToBotCreation }
            Event.Ui.OpenAccount -> effects { +Effect.NavigateToAccount }
            is Event.Ui.OpenBot -> effects { +Effect.NavigateToBot(event.botId) }
            is Event.Ui.SwitchBotModeClick -> commands {
                +Command.ChangeBotStatus(
                    event.botId,
                    event.isWorking
                )
            }
        }
    }

    override fun Result.internal(event: Event.Domain) {
        when (event) {
            is Event.Domain.Error -> state {
                copy(
                    isError = true
                )
            }

            is Event.Domain.LoadData -> state {
                copy(data = event.bots.toMutableList(), isLoading = false)
            }
            is HomeEvent.Domain.UpdateBot -> state {
                val updatedBots: MutableList<Bot> = state.data.map { bot ->
                    if (bot.id == event.bot.id) event.bot else bot
                }.toMutableList()
                copy(data = updatedBots)
            }
        }
    }
}
