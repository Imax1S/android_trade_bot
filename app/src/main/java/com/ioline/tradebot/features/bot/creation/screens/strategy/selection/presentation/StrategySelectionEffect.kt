package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation

import com.ioline.tradebot.data.models.Bot

internal sealed class StrategySelectionEffect {
    object OpenPreviousScreen : StrategySelectionEffect()
    data class OpenStrategy(val bot: Bot, val strategyId: String) : StrategySelectionEffect()
    data class ShowStrategyHint(val strategyId: String) : StrategySelectionEffect()
}
