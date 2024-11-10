package com.ioline.tradebot.data.repository.instrument

import com.ioline.tradebot.data.models.Instrument
import kotlinx.coroutines.flow.Flow

class InstrumentRepositoryImpl : InstrumentRepository {
    override fun searchInstrument(text: String): Flow<SearchResult<List<Instrument>>> {
        TODO("Not yet implemented")
    }
}