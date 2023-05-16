package com.ioline.tradebot.data.models

import java.io.Serializable

data class HistoricalResult(
    val finalBalance: Double,
    val growth: Double
) : Serializable {
    companion object {
        const val serialVersionUID = 123L
    }
}
