package com.ioline.tradebot.features.home.presentation

import com.ioline.tradebot.data.models.Bot

internal data class HomeState(
    val data: MutableList<Bot> = mutableListOf(),
    val isError: Boolean = false,
    val isLoading: Boolean = true
)