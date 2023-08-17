package com.ioline.tradebot.features.home.presentation.homescreen

internal sealed class HomeEffect {
    data class NavigateToBot(val botId: String) : HomeEffect()
    object NavigateToBotCreation : HomeEffect()
    object NavigateToAccount : HomeEffect()
}

internal sealed class HomeCommand {
    // your code
    object LoadData : HomeCommand()
    data class SwitchBotWorking(val botId: String, val isWorking: Boolean) : HomeCommand()
}
