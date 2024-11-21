package com.ioline.tradebot.features.bot.creation.screens.params.presentation

internal sealed interface BotCreationEffect {
    data object Close : BotCreationEffect
    data class OpenStrategySelection(val botId: String) : BotCreationEffect
}
