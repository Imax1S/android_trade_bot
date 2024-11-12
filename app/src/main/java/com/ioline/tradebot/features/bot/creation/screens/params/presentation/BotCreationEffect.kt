package com.ioline.tradebot.features.bot.creation.screens.params.presentation

import com.ioline.tradebot.data.models.Bot

internal sealed interface BotCreationEffect {
    data object Close : BotCreationEffect
    data class OpenStrategySelection(val bot: Bot) : BotCreationEffect
}
