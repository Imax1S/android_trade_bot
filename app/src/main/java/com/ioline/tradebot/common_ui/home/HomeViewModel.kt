package com.ioline.tradebot.common_ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ioline.tradebot.App
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.Instrument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val INSTRUMENT_DELIMITER = " "
    }

    private val repository = (application as App).repository

    private val _bots = MutableLiveData<MutableList<Bot>>().apply {
        value = mutableListOf()
    }
    val instruments = MutableLiveData<List<Instrument?>>().apply {
        value = mutableListOf()
    }

    val bots: MutableLiveData<MutableList<Bot>> = _bots

    fun saveBot(bot: Bot?) {
        if (bot != null) {
            val list = _bots.value
            list?.add(bot)
            list?.let {
                bots.postValue(it)
            }
        }
    }

    fun setupInstruments(instrumentsString: String?) {
        instrumentsString ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val newInstruments = instrumentsString.split(INSTRUMENT_DELIMITER).map {
                val instrument = repository.findInstrument(it)
                val price = repository.getPrice(instrument?.figi ?: "")
                Log.d("TAGA", instrument.toString())
                instrument?.copy(
                    price = price
                )
            }
            instruments.postValue(newInstruments)
        }
    }

    fun createBot(bot: Bot?) {
        bot ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val newBot = bot.copy(
//                id = UUID.randomUUID().toString()

                instrumentsFIGI = listOf(instruments.value?.map { it?.figi ?: "" }?.first() ?: "") ?: listOf()
            )
            repository.createBot(newBot)
            val list = _bots.value
            list?.add(newBot)
            list?.let {
                bots.postValue(it)
            }
        }
    }

    fun runBot(bot: Bot) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.runBot(bot)
            result ?: return@launch
            val newBot = bot.copy(
                result = result
            )
            val list = _bots.value
            list?.add(newBot)
            list?.let {
                bots.postValue(mutableListOf(newBot))
            }
        }
    }
}
