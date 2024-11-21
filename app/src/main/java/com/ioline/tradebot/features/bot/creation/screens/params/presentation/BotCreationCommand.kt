package com.ioline.tradebot.features.bot.creation.screens.params.presentation

import com.ioline.tradebot.data.models.Bot

internal sealed class BotCreationCommand {
    data class SearchInstrument(val text: String) : BotCreationCommand()
    data class SaveBotLocally(val bot: Bot) : BotCreationCommand()
}
