package com.ioline.tradebot.data.models

import java.io.Serializable

data class TimeSettings(
    val interval: CandleInterval,
    val start: String,
    val end: String,
    val period: TimePeriod
) : Serializable {
    companion object {
        const val serialVersionUID = 123L
    }
}
