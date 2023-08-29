@file:Suppress("DEPRECATION")

package com.ioline.tradebot

import android.os.Build
import android.os.Bundle
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.Instrument
import com.ioline.tradebot.data.models.strategy.StrategyManual


fun Bundle.getBotSerializable(key: String) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializable(key, Bot::class.java)
    } else {
        this.getSerializable(key) as Bot
    }


fun Bundle.getInstrumentSerializable(key: String) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializable(key, Instrument::class.java)
    } else {
        this.getSerializable(key) as Instrument
    }

fun Bundle.getStrategySerializable(key: String) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializable(key, StrategyManual::class.java)
    } else {
        this.getSerializable(key) as Instrument
    }