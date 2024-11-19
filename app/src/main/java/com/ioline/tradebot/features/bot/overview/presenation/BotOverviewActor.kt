package com.ioline.tradebot.features.bot.overview.presenation

import com.ioline.tradebot.features.bot.overview.presenation.BotOverviewEvent.Internal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

internal class BotOverviewActor @Inject constructor(
    // your dependencies
) : Actor<BotReviewCommand, Internal> {

    override fun execute(
        command: BotReviewCommand
    ): Flow<Internal> = flow {
        when (command) {
            is BotReviewCommand.Init -> {
            }
        }
    }
}