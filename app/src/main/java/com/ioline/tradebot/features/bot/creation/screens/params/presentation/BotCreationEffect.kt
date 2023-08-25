package com.ioline.tradebot.features.bot.creation.screens.params.presentation

internal sealed interface BotCreationEffect {
    object Close : BotCreationEffect
    object OpenStrategySelection : BotCreationEffect
}
