package com.ioline.tradebot.features.bot.overview.presenation

internal sealed class BotReviewCommand {
    data class Init(val botId: String) : BotReviewCommand()
    data class RunBotOnHistoricalData(val botId: String) : BotReviewCommand()
}