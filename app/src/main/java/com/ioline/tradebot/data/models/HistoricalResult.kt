package com.ioline.tradebot.data.models

import java.io.Serializable

data class HistoricalResult(
    val finalBalance: Double,
    val yield: Double
) : Serializable {
    companion object {
        const val serialVersionUID = 123L
    }
}
