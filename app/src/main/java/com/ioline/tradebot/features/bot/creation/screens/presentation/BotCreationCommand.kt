package com.ioline.tradebot.features.bot.creation.screens.presentation

internal sealed class BotCreationCommand {
    data class SearchInstrument(val text: String) : BotCreationCommand()
}
