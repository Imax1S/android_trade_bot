package com.ioline.tradebot.features.bot.review.presenation

import com.ioline.tradebot.features.bot.review.presenation.BotReviewEvent.Internal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

internal class BotReviewActor @Inject constructor(
    // your dependencies
) : Actor<BotReviewCommand, Internal> {

    override fun execute(
        command: BotReviewCommand
    ): Flow<Internal> = flow {
        when (command) {
            else -> {}
        }
    }
}