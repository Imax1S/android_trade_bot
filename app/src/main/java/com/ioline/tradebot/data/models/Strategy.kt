package com.ioline.tradebot.data.models

import java.io.Serializable


data class Strategy(
    val type: StrategyType,
    val param1: String,
    val param2: String,
) : Serializable {
    companion object {
        const val serialVersionUID = 123L
    }
}
