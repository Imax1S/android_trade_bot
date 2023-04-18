package com.ioline.tradebot.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.Instrument

class HomeViewModel : ViewModel() {
    companion object {
        const val INSTRUMENT_DELIMITER = " "
    }

    private val instruments = mutableListOf(Instrument("APPL"), Instrument("YANDX"))
    private val _bots = MutableLiveData<MutableList<Bot>>().apply {
        value = mutableListOf(
//            Bot("Richest bot", OperationMode.MANUAL, MarketEnvironment.HISTORICAL_DATA, instruments)
        )
    }
    val bots: LiveData<MutableList<Bot>> = _bots

    fun saveBot(bot: Bot?) {
        if (bot != null) {
            _bots.value?.add(bot)
        }
    }

    fun getInstruments(instruments: String): List<Instrument> {
        return instruments.split(INSTRUMENT_DELIMITER).map {
            Instrument(it)
        }
    }
}