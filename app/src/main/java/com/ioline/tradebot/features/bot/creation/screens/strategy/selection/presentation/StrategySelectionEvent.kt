package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation

internal sealed class StrategySelectionEvent {
    sealed class Domain : StrategySelectionEvent() {}

    sealed class Ui : StrategySelectionEvent() {
        object System {
            object Init : Ui()
        }

        object Click {
            data class OpenStrategy(val strategyId: String) : Ui()
            data object BackToPreviousScreen : Ui()
            data class ShowStrategyHint(val strategyId: String) : Ui()
            data object Next : Ui()
        }
    }
}
