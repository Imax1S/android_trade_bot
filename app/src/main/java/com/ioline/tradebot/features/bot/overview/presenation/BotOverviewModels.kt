package com.ioline.tradebot.features.bot.overview.presenation

import com.ioline.tradebot.data.models.Bot

internal data class BotReviewState(
    val bot: Bot,
    val selectedPeriod: ChartPeriod = ChartPeriod.ALL,
    val dataForSelectedPeriod: List<Double> = emptyList()
)

enum class ChartPeriod(name: String) {
    ALL("All"),
    YEAR("Year"),
    SIX_MONTHS("6 Months"),
    MONTH("Month"),
    WEEK("Week"),
    DAY("Day");
}

internal sealed class BotReviewEffect {
    data object Close : BotReviewEffect()
    data object OpenStrategy : BotReviewEffect()
}

internal sealed class BotReviewCommand {
    data class Init(val bot: Bot) : BotReviewCommand()
}