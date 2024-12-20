package com.ioline.tradebot.features.bot.creation.screens.environment.presentation

import com.ioline.tradebot.data.models.CandleInterval
import com.ioline.tradebot.data.models.TimePeriod
import com.ioline.tradebot.data.models.TimeSettings
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsEvent.Internal
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsEvent.Ui
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
        is Internal.LoadedData -> state {
            copy(
                bot = event.bot
            )
        }
        Internal.CloseBotCreation -> effects {
            +Effect.NavigateToHome
        }
    }

    override fun Result.ui(event: Ui) = when (event) {
        is Ui.System.Init -> {
            commands {
                +Command.LoadData(event.botId)
            }
        }
        Ui.Click.SaveBot -> commands {
            val updatedBot = state.bot?.copy(
                timeSettings = TimeSettings(
                    interval = CandleInterval.CANDLE_INTERVAL_DAY,
                    start = state.startDate.toString(),
                    end = state.endDate.toString(),
                    period = TimePeriod.YEARS
                )
            )
            +updatedBot?.let { Command.UpdateBotEnvironment(it) }
        }
        is Ui.Click.SelectEndDate -> commands { Command.ValidateDate(event.date) }
        is Ui.Click.SelectStartDate -> commands { Command.ValidateDate(event.date) }
        Ui.Click.ShowInstruction -> effects { +Effect.ShowInstruction }
        is Ui.Click.ValidateToken -> commands { +Command.ValidateToken(event.token) }
    }
}