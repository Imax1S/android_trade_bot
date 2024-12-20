package com.ioline.tradebot.features.bot.creation.screens.environment.presentation

import com.ioline.tradebot.data.models.Bot
import java.util.Date

internal sealed class EnvironmentSettingsEvent {
    sealed class Internal : EnvironmentSettingsEvent() {
        data object ShowTokenIsCorrect : Internal()
        data object ShowTokenIsWrong : Internal()
        data object ShowDateRangeIsIncorrect : Internal()
        data class LoadedData(val bot: Bot?) : Internal()
        data object CloseBotCreation : Internal()
    }

    sealed class Ui : EnvironmentSettingsEvent() {
        object System {
            data class Init(val botId: String) : Ui()
        }

        object Click {
            data class SelectStartDate(val date: Date) : Ui()
            data class SelectEndDate(val date: Date) : Ui()
            data object SaveBot : Ui()
            data class ValidateToken(val token: String) : Ui()
            data object ShowInstruction : Ui()
        }
    }
}