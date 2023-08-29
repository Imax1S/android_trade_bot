package com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation

import com.ioline.tradebot.data.models.strategy.StrategyType
import com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation.StrategySetupCommand as Command
import com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation.StrategySetupEvent as Event
import com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation.StrategySetupEvent.Ui
import com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation.StrategySetupEvent.Internal
import com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation.StrategySetupState as State
import com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation.StrategySetupEffect as Effect
import vivid.money.elmslie.core.store.dsl.ScreenDslReducer

internal object StrategySetupReducer :
    ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {
    override fun Result.ui(event: Ui) = when (event) {
        is Ui.System.Init -> {
            when (event.strategy.type) {
                StrategyType.EMA -> TODO()
                StrategyType.RSI -> TODO()
                StrategyType.CUSTOM -> TODO()
            }
        }
    }

    override fun Result.internal(event: Internal) = when (event) {

    }
}