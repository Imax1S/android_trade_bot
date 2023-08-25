package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation

import vivid.money.elmslie.core.store.Actor
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent.Domain
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionCommand as Command
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class StrategySelectionActor @Inject constructor() : Actor<Command, Domain>() {
    override fun execute(
        command: Command
    ): Flow<Domain> = flow {  }
}