package com.ioline.tradebot.features.bot.overview.presenation

internal sealed class BotReviewEffect {
    data object Close : BotReviewEffect()
    data object OpenStrategy : BotReviewEffect()
}