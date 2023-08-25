package com.ioline.tradebot.features.bot.creation.screens.params.presentation

internal sealed class BotCreationCommand {
    data class SearchInstrument(val text: String) : BotCreationCommand()
}
