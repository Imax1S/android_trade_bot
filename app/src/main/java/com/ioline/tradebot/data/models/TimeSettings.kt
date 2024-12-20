package com.ioline.tradebot.data.models

import kotlinx.serialization.Serializable

@Serializable
data class TimeSettings(
    val interval: CandleInterval,
    val start: String,
    val end: String,
    val period: TimePeriod
)