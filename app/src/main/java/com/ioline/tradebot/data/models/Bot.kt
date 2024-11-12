package com.ioline.tradebot.data.models

import com.ioline.tradebot.data.models.strategy.Strategy
import java.io.Serializable

data class Bot(
    val id: String = "0",
    val name: String,
    val description: String = "",
    val strategy: Strategy? = null,
    var isActive: Boolean = false,
    val instrumentsFIGI: List<String> = emptyList(),
    val marketEnvironment: MarketEnvironment,
    val timeSettings: TimeSettings? = null,
//    val mode: OperationMode,
    val result: HistoricalResult? = null
) : Serializable {
    companion object {
        const val serialVersionUID = 123L
    }
}
