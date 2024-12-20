package com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation

import com.ioline.tradebot.data.models.strategy.Strategy

internal data class StrategySetupState(
    val strategy: Strategy
)

internal sealed class StrategySetupEffect {
    data object OpenPreviousScreen : StrategySetupEffect()
    data object FinishBotCreation : StrategySetupEffect()
}

internal sealed class StrategySetupCommand

internal sealed class StrategySetupEvent {
    sealed class Internal : StrategySetupEvent() {
        // your code
    }

    sealed class Ui : StrategySetupEvent() {
        object System {
            data class Init(val strategy: Strategy) : Ui()
        }

        object Click {
            data object ShowHint : Ui()
            data object Launch : Ui()
            data object Create : Ui()
        }
    }
}