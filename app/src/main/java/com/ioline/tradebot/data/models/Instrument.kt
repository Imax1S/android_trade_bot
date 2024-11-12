package com.ioline.tradebot.data.models

import java.io.Serializable

data class Instrument(
    val classCode: String,
    val figi: String,
    val first1dayCandleDate: String,
    val first1minCandleDate: String,
    val forIisFlag: Boolean,
    val instrumentKind: String,
    val instrumentType: String,
    val isin: String,
    val name: String,
    val positionUid: String,
    val ticker: String,
    val uid: String,
    val weekendFlag: Boolean,
    val price: Double = 0.0
): Serializable  {
    companion object {
        const val serialVersionUID = 123L
    }
}