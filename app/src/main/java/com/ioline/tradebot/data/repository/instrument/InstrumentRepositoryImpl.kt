package com.ioline.tradebot.data.repository.instrument

import com.ioline.tradebot.data.models.Instrument
import com.ioline.tradebot.domain.TradeBotApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InstrumentRepositoryImpl @Inject constructor(
    private val tradeBotApi: TradeBotApi
) : InstrumentRepository {
    override fun searchInstrument(text: String): Flow<SearchResult<List<Instrument>>> = flow {
        val result = tradeBotApi.findInstruments(text)

        when {
            result.isSuccessful -> {
                if (result.body()?.isEmpty() == true || result.body() == null) {
                    emit(SearchResult.NoResult)
                } else {
                    emit(SearchResult.Success(result.body()))
                }
            }
            else -> emit(SearchResult.Error(result.message()))
        }
    }
}