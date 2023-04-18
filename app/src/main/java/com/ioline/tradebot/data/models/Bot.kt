package com.ioline.tradebot.data.models

import java.io.Serializable

data class Bot(
    val name: String,
    val mode: OperationMode,
    val environment: MarketEnvironment,
    val instruments: List<Instrument>
) : Serializable
