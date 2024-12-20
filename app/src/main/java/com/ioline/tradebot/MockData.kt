package com.ioline.tradebot

import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment

val botsMock = mutableListOf(
    Bot(
        id = "0",
        name = "Edwin Hopper 0",
        description = "Some description",
        strategy = null,
        isActive = false,
        instrumentsFIGI = listOf(),
        marketEnvironment = MarketEnvironment.MARKET,
        timeSettings = null,
        result = null
    ),
    Bot(
        id = "1",
        name = "Edwin Hopper 1",
        description = "Some description",
        strategy = null,
        isActive = false,
        instrumentsFIGI = listOf(),
        marketEnvironment = MarketEnvironment.MARKET,
        timeSettings = null,
        result = null
    ),
    Bot(
        id = "2",
        name = "Edwin Hopper 2",
        description = "Some description",
        strategy = null,
        isActive = true,
        instrumentsFIGI = listOf(),
        marketEnvironment = MarketEnvironment.MARKET,
        timeSettings = null,
        result = null
    ),
    Bot(
        id = "3",
        name = "Edwin Hopper 3",
        description = "Some description",
        strategy = null,
        isActive = false,
        instrumentsFIGI = listOf(),
        marketEnvironment = MarketEnvironment.MARKET,
        timeSettings = null,
        result = null
    ),
    Bot(
        id = "4",
        name = "Edwin Hopper 4",
        description = "Some description",
        strategy = null,
        isActive = false,
        instrumentsFIGI = listOf(),
        marketEnvironment = MarketEnvironment.MARKET,
        timeSettings = null,
        result = null
    )
)