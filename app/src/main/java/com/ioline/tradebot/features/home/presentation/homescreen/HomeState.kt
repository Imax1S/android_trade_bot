package com.ioline.tradebot.features.home.presentation.homescreen

import com.ioline.tradebot.data.models.Bot

internal data class HomeState(
    val data: List<Bot> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = false
)