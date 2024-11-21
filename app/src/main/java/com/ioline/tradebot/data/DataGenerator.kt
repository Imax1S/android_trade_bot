package com.ioline.tradebot.data

import kotlin.random.Random

fun generateStockData(
    points: Int = 365,
    startPrice: Int = 0,
    volatility: Double = 30.0,
    trend: Double = 0.1
): List<Int> {
    val prices = mutableListOf(startPrice)
    val random = Random(System.currentTimeMillis())

    for (i in 1 until points) {
        val previousPrice = prices.last()
        val randomShock = (random.nextDouble() - 0.5) * volatility
        val newPrice = previousPrice + randomShock + trend
        prices.add(newPrice.coerceIn(10.0, 200.0).toInt())
    }

    return prices
}