package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation

import com.ioline.tradebot.data.models.strategy.StrategyType

internal sealed class StrategySelectionEvent {
    sealed class Domain : StrategySelectionEvent() {
        data class Next(val botId: String) : Domain()
    }

    sealed class Ui : StrategySelectionEvent() {
        object System {
            data object Init : Ui()
        }

        object Click {
            data class OpenStrategy(val strategyId: String) : Ui()
            data object BackToPreviousScreen : Ui()
            data class ShowStrategyHint(val strategyId: String) : Ui()
            data object Next : Ui()
            data class ChangePeriod1Param(val value: Float) : Ui()
            data class ChangePeriod2Param(val value: Float) : Ui()
            data object RegenerateRandomData : Ui()
            data class SelectStrategy(val selectedStrategyType: StrategyType) : Ui()
        }
    }
}
