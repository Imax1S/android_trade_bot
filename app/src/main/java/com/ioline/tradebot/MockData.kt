package com.ioline.tradebot

import com.ioline.tradebot.data.models.Instrument
import com.ioline.tradebot.data.models.Operation
import com.ioline.tradebot.data.models.OrderType

val mockNumbers = listOf(
    7.7246, -3.6341, -3.9211, -4.5340, 9.8849, 7.4460, 7.5853, -3.2039, 8.9593, -2.2687,
    8.2789, -0.2491, -6.3714, -0.5985, -9.5413, -8.2540, -9.7241, -3.5890, 8.5986, -3.3505,
    -5.0098, -8.7844, -6.5741, 0.0867, 8.1335, 8.7577, 1.8287, 0.4208, 5.4705, 2.1953,
    4.3873, -3.3048, 5.9953, 0.0137, -0.7031, -8.6189, 0.4413, 7.5607, 8.4054, -4.6039
)
val mockBaseInstrument = Instrument(
    classCode = "lorem",
    figi = "omnesque",
    first1dayCandleDate = "civibus",
    first1minCandleDate = "inceptos",
    forIisFlag = false,
    instrumentType = "viderer",
    name = "Apple",
    ticker = "TICKER92",
    uid = "saperet",
    price = 4513.12
)

val mockInstruments = listOf(
    mockBaseInstrument.copy(name = "Amazon", price = 1314.3),
    mockBaseInstrument.copy(name = "Google", price = 1688.45, ticker = "TICKER85"),
    mockBaseInstrument.copy(name = "Netflix", price = 3119.11, ticker = "TICKER93"),
    mockBaseInstrument.copy(name = "Microsoft", price = 1509.98, ticker = "TICKER50"),
    mockBaseInstrument.copy(name = "Meta", price = 4680.13, ticker = "TICKER84"),
    mockBaseInstrument.copy(name = "Alibaba", price = 3978.69, ticker = "TICKER97"),
    mockBaseInstrument.copy(name = "Samsung", price = 2205.71, ticker = "TICKER92"),
    mockBaseInstrument.copy(name = "IBM", price = 2457.11, ticker = "TICKER78"),
    mockBaseInstrument.copy(name = "Intel", price = 386.80, ticker = "TICKER45")
)

val mockOperations = listOf(
    Operation(
        type = OrderType.SELL,
        asset = mockInstruments[0],
        price = 1968.89,
        date = "2024-11-19"
    ),
    Operation(
        type = OrderType.SELL,
        asset = mockInstruments[2],
        price = 4365.52,
        date = "2024-11-10"
    ),
    Operation(
        type = OrderType.SELL,
        asset = mockInstruments[1],
        price = 1413.71,
        date = "2024-11-06"
    ),
    Operation(
        type = OrderType.SELL,
        asset = mockInstruments[3],
        price = 1778.08,
        date = "2024-11-17"
    ),
    Operation(
        type = OrderType.SELL,
        asset = mockInstruments[5],
        price = 2786.24,
        date = "2024-11-05"
    ),
    Operation(
        type = OrderType.BUY,
        asset = mockInstruments[1],
        price = 719.86,
        date = "2024-10-23"
    ),
    Operation(
        type = OrderType.SELL,
        asset = mockInstruments[4],
        price = 1853.42,
        date = "2024-11-17"
    ),
    Operation(
        type = OrderType.SELL,
        asset = mockInstruments[2],
        price = 2433.08,
        date = "2024-11-16"
    ),
    Operation(
        type = OrderType.SELL,
        asset = mockInstruments[1],
        price = 1967.27,
        date = "2024-11-01"
    ),
    Operation(
        type = OrderType.BUY,
        asset = mockInstruments[0],
        price = 3036.49,
        date = "2024-10-30"
    )
)