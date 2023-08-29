package com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.strategy.Strategy

internal data class StrategySetupState(
    val strategy: Strategy
)

internal sealed class StrategySetupEffect {
    object OpenPreviousScreen : StrategySetupEffect()
    object FinishBotCreation : StrategySetupEffect()
}

internal sealed class StrategySetupCommand {
    data class CreateBot(val bot: Bot) : StrategySetupCommand()
}

internal sealed class StrategySetupEvent {
    sealed class Internal : StrategySetupEvent() {
        // your code
    }

    sealed class Ui : StrategySetupEvent() {
        object System {
            data class Init(val strategy: Strategy) : Ui()
        }

        object Click {
            object ShowHint : Ui()
            object Launch : Ui()
            object Create : Ui()
        }

        object Action {
            // your code
        }
    }
}