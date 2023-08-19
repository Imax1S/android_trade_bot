package com.ioline.tradebot.features.bot.creation.screens.presentation

import com.ioline.tradebot.data.models.Instrument
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.models.OperationMode

internal data class BotCreationState(
    val name: String,
    val description: String,
    val marketEnvironment: MarketEnvironment = MarketEnvironment.HISTORICAL_DATA,
    val mode: OperationMode = OperationMode.AUTO,
    val searchInstruments: List<Instrument> = emptyList(),
    val selectedInstruments: List<Instrument> = emptyList(),
    val searchInstrumentsLoading: Boolean = false,
    val searchInstrumentError: Boolean = false
)
