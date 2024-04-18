package com.ioline.tradebot.features.home.presentation.homescreen

import com.ioline.tradebot.data.models.Bot

internal sealed class HomeEvent {

    sealed class Ui : HomeEvent() {
        data object Init : Ui()
        data object ReloadClick : Ui()
        data class OpenBot(val botId: String) : Ui()
        data class SwitchBotModeClick(val botId: String, val isWorking: Boolean) : Ui()
        data object CreateNewBotClick : Ui()
        data object OpenAccount : Ui()
    }

    sealed class Domain : HomeEvent() {
        data class LoadData(val bots: List<Bot>) : Domain()
        data class Error(val error: Throwable) : Domain()
    }
}
