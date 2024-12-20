package com.ioline.tradebot.features.bot.overview.presenation

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.Operation

internal data class BotReviewState(
    val bot: Bot? = null,
    val selectedPeriod: ChartPeriod = ChartPeriod.ALL,
    val dataForSelectedPeriod: List<Double> = emptyList(),
    val yield: Double = 0.0,
    val operations: List<Operation> = emptyList(),
    val runData: List<Double> = emptyList(),
    val inEditMode: Boolean = false,
)

enum class ChartPeriod {
    ALL,
    YEAR,
    SIX_MONTHS,
    MONTH,
    WEEK,
    DAY;
}