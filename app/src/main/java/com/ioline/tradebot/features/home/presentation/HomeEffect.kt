package com.ioline.tradebot.features.home.presentation

internal sealed class HomeEffect {
    data class NavigateToBot(val botId: String) : HomeEffect()
    data object NavigateToBotCreation : HomeEffect()
    data object NavigateToAccount : HomeEffect()
}