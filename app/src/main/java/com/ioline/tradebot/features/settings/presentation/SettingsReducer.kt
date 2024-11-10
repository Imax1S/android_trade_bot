package com.ioline.tradebot.features.settings.presentation

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

internal object SettingsReducer : DslReducer<SettingsEvent, SettingsState,
        SettingsEffect, SettingsCommand>() {

    override fun Result.reduce(event: SettingsEvent) = when (event) {
        else -> TODO("Not yet implemented")
    }
}