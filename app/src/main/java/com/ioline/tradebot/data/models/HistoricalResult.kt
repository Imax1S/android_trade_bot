package com.ioline.tradebot.data.models

import kotlinx.serialization.Serializable

@Serializable
data class HistoricalResult(
    val finalBalance: Double,
    val yield: Double,
    val history: List<Double> = emptyList(),
    val operations: List<Operation> = emptyList()
)