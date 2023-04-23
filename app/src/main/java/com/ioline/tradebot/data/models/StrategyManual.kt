package com.ioline.tradebot.data.models

import java.io.Serializable

data class StrategyManual(
    val sellAllDropPercentages: Double,
    val sellAllIncreasePercentages: Double,
    val buyAllDropPercentages: Double,
) : Serializable {
    companion object {
        const val serialVersionUID = 123L
    }
}
