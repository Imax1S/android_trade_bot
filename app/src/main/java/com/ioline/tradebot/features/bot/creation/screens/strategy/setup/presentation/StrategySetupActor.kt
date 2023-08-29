package com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation

import vivid.money.elmslie.core.store.Actor
import com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation.StrategySetupEvent.Internal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class StrategySetupActor @Inject constructor(
    // your dependencies
) : Actor<StrategySetupCommand, Internal>() {

    override fun execute(
        command: StrategySetupCommand
    ): Flow<Internal> = flow{

    }
}