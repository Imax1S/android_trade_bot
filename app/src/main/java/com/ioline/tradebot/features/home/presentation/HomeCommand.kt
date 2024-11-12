package com.ioline.tradebot.features.home.presentation

internal sealed class HomeCommand {
    data object LoadData : HomeCommand()
    data class ChangeBotStatus(val botId: String, val isActive: Boolean) : HomeCommand()
}