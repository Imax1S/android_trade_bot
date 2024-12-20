package com.ioline.tradebot.data.repository.instrument

import com.ioline.tradebot.data.models.Instrument
import kotlinx.coroutines.flow.Flow

interface InstrumentRepository {
    fun searchInstrument(text: String): Flow<SearchResult<List<Instrument>>>
}