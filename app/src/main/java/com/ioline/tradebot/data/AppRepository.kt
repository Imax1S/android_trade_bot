package com.ioline.tradebot.data

import android.util.Log
import com.ioline.tradebot.data.models.Instrument
import com.ioline.tradebot.domain.TinkoffApi

class AppRepository(private val tinkoffApi: TinkoffApi) {

    suspend fun findInstrument(id: String): Instrument? {
          var instrument: Instrument? = null
            try {
                val respone = tinkoffApi.findInstrument(id)
                if (respone.isSuccessful) {
                    instrument = respone.body()
                }
            } catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
            }

        return instrument
    }
}
