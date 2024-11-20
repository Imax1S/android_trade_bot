package com.ioline.tradebot.data.models

import com.ioline.tradebot.data.models.strategy.Strategy
import kotlinx.serialization.Serializable

@Serializable
data class Bot(
    val id: String = "0",
    val name: String,
    val description: String = "",
    val strategy: Strategy? = null,
    var isActive: Boolean = false,
    val instrumentsFIGI: List<String> = emptyList(),
    val marketEnvironment: MarketEnvironment,
    val timeSettings: TimeSettings? = null,
    val mode: OperationMode = OperationMode.MANUAL,
    val result: HistoricalResult? = null,
    val operations: List<Operation> = emptyList(),
    val assets: List<Instrument> = emptyList()
)