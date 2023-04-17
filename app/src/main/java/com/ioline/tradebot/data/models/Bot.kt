package com.ioline.tradebot.data.models


data class Bot(
    val name: String,
    val mode: OperationMode,
    val environment: MarketEnvironment,
    val instruments: List<Instrument>
)
