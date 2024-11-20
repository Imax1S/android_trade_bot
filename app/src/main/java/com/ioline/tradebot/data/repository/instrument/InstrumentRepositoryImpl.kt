package com.ioline.tradebot.data.repository.instrument

import com.ioline.tradebot.data.models.Instrument
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InstrumentRepositoryImpl : InstrumentRepository {
    override fun searchInstrument(text: String): Flow<SearchResult<List<Instrument>>> = flow {
        val results = if (text.isNotEmpty() && text.isNotBlank()) {
            instrumentsMock.filter { it.ticker.contains(text, ignoreCase = true) }
        } else {
            emptyList()
        }

        when {
            results.isNotEmpty() -> emit(SearchResult.Success(results))
            results.isEmpty() -> emit(SearchResult.NoResult)
        }
    }
}

val instrumentsMock = listOf(
    Instrument(
        classCode = "AAPL",
        figi = "BBG000B9XRY4",
        first1dayCandleDate = "2000-01-01",
        first1minCandleDate = "2010-01-01",
        forIisFlag = true,
        instrumentType = "Equity",
        name = "Apple Inc.",
        ticker = "AAPL",
        uid = "uid1",
        price = 150.0
    ),
    Instrument(
        classCode = "GOOGL",
        figi = "BBG009S39JX6",
        first1dayCandleDate = "2004-08-19",
        first1minCandleDate = "2014-08-19",
        forIisFlag = false,
        instrumentType = "Equity",
        name = "Alphabet Inc.",
        ticker = "GOOGL",
        uid = "uid2",
        price = 2800.0
    ),
    Instrument(
        classCode = "TSLA",
        figi = "BBG000N9MNX3",
        first1dayCandleDate = "2010-06-29",
        first1minCandleDate = "2015-06-29",
        forIisFlag = true,
        instrumentType = "Equity",
        name = "Tesla Inc.",
        ticker = "TSLA",
        uid = "uid3",
        price = 800.0
    ),
    Instrument(
        classCode = "MSFT",
        figi = "BBG000BPH459",
        first1dayCandleDate = "1986-03-13",
        first1minCandleDate = "1990-01-01",
        forIisFlag = false,
        instrumentType = "Equity",
        name = "Microsoft Corp.",
        ticker = "MSFT",
        uid = "uid4",
        price = 299.0
    ),
    Instrument(
        classCode = "AMZN",
        figi = "BBG000BVPV84",
        first1dayCandleDate = "1997-05-15",
        first1minCandleDate = "2000-05-15",
        forIisFlag = true,
        instrumentType = "Equity",
        name = "Amazon.com Inc.",
        ticker = "AMZN",
        uid = "uid5",
        price = 3300.0
    )
)