package com.ioline.tradebot.features.bot.creation.screens.environment.presentation

import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsEvent.Internal
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsEvent.Ui
import vivid.money.elmslie.core.store.Result
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsCommand as Command
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsEffect as Effect
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsEvent as Event
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsState as State

internal object EnvironmentSettingsReducer : ScreenDslReducer<
        Event,
        Ui,
        Internal,
        State,
        Effect,
        Command>(Ui::class, Internal::class) {

    override fun Result.internal(event: Internal) = when (event) {
        Internal.ShowDateRangeIsIncorrect -> TODO()
        Internal.ShowTokenIsCorrect -> TODO()
        Internal.ShowTokenIsWrong -> TODO()
    }

    override fun Result.ui(event: Ui) = when (event) {
        Ui.System.Init -> {}
        Ui.Click.SaveBot -> effects { +Effect.NavigateToHome }
        is Ui.Click.SelectEndDate -> commands { Command.ValidateDate(event.date) }
        is Ui.Click.SelectStartDate -> commands { Command.ValidateDate(event.date) }
        Ui.Click.ShowInstruction -> effects { +Effect.ShowInstruction }
        is Ui.Click.ValidateToken -> commands { +Command.ValidateToken(event.token) }
    }
}