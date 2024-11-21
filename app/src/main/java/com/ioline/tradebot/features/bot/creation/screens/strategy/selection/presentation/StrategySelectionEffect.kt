package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation


internal sealed class StrategySelectionEffect {
    data object OpenPreviousScreen : StrategySelectionEffect()
    data class Next(val botId: String) : StrategySelectionEffect()
    data class ShowStrategyHint(val strategyId: String) : StrategySelectionEffect()
}
