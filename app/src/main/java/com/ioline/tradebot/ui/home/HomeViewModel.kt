package com.ioline.tradebot.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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

    val bots: LiveData<MutableList<Bot>> = _bots

    fun saveBot(bot: Bot?) {
        if (bot != null) {
            _bots.value?.add(bot)
        }
    }

    fun setupInstruments(instrumentsString: String?) {
        instrumentsString ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val newInstruments = instrumentsString.split(INSTRUMENT_DELIMITER).map {
                val instrument = repository.findInstrument(it)
                Log.d("TAGA", instrument.toString())
                instrument
            }
            instruments.postValue(newInstruments)
        }
    }
}
