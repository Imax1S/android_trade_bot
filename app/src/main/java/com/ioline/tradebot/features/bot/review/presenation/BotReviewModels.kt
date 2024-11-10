package com.ioline.tradebot.features.bot.review.presenation

import com.ioline.tradebot.data.models.Bot

internal data class BotReviewState(
    val bot: Bot
)

internal sealed class BotReviewEffect {
    data object Close : BotReviewEffect()
    data object OpenStrategy : BotReviewEffect()
}

internal sealed class BotReviewCommand {
    // your code
}