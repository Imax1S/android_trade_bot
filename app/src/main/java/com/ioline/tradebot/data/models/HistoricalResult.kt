package com.ioline.tradebot.data.models

import java.io.Serializable

data class HistoricalResult(
    val finalBalance: Double,
    val yield: Double,
    val history: List<Double> = emptyList()
) : Serializable {
    companion object {
        const val serialVersionUID = 123L
    }
}
