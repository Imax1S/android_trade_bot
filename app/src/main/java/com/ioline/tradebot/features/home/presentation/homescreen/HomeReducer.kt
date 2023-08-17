package com.ioline.tradebot.features.home.presentation.homescreen

import com.ioline.tradebot.features.home.presentation.homescreen.HomeEvent as Event
import com.ioline.tradebot.features.home.presentation.homescreen.HomeCommand as Command
import com.ioline.tradebot.features.home.presentation.homescreen.HomeEffect as Effect
import vivid.money.elmslie.core.store.dsl.ScreenDslReducer

internal object HomeReducer :
    ScreenDslReducer<Event, Event.Ui, Event.Domain, HomeState, Effect, Command>(
        Event.Ui::class,
        Event.Domain::class
    ) {
    override fun Result.ui(event: Event.Ui) {
        when (event) {
            Event.Ui.Init -> commands { +Command.LoadData }
            Event.Ui.ReloadClick -> commands { +Command.LoadData }
            Event.Ui.CreateNewBotClick -> effects { +Effect.NavigateToBotCreation }
            Event.Ui.OpenAccount -> effects { +Effect.NavigateToAccount }
            is Event.Ui.OpenBot -> effects { +Effect.NavigateToBot(event.botId) }
            is Event.Ui.SwitchBotModeClick -> commands {
                +Command.SwitchBotWorking(
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
                copy(data = event.bots)
            }
        }
    }
}
