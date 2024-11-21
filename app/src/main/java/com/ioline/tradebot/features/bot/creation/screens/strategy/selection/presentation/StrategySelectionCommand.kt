package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation

import com.ioline.tradebot.data.models.strategy.Strategy

internal sealed class StrategySelectionCommand {
    data class UpdateBotStrategy(
        val botId: String,
        val strategy: Strategy
    ) : StrategySelectionCommand()
}
