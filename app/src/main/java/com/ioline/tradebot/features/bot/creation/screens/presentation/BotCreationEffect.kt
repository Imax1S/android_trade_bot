package com.ioline.tradebot.features.bot.creation.screens.presentation

internal sealed interface BotCreationEffect {
    object Close : BotCreationEffect
    object OpenStrategySelection : BotCreationEffect
}
