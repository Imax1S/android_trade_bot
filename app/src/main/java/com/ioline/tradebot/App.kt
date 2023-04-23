package com.ioline.tradebot

import android.app.Application
import com.ioline.tradebot.data.AppRepository
import com.ioline.tradebot.domain.RetrofitHelper
import com.ioline.tradebot.domain.TinkoffApi

class App : Application() {
    private val tinkoffApi by lazy { RetrofitHelper.getInstance().create(TinkoffApi::class.java) }
    val repository by lazy { AppRepository(tinkoffApi) }
}
