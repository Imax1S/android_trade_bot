package com.ioline.tradebot.data.models

data class Deal(
    val type: OrderType,
    val asset: Instrument,
    val price: Double,
    val date: String,
)

enum class OrderType {
    SELL,
    BUY
}