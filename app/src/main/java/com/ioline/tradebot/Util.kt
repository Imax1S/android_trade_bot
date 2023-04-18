package com.ioline.tradebot

import android.os.Build
import android.os.Bundle
import com.ioline.tradebot.data.models.Bot


fun Bundle.getBotSerializable(key: String) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializable(key, Bot::class.java)
    } else {
        this.getSerializable(key) as Bot
    }