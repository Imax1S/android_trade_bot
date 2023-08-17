package com.ioline.tradebot.data.repository

sealed class WorkResult<out R> {
    data class Success<out T>(val data: T) : WorkResult<T>()
    data class Error(val throwable: Throwable) : WorkResult<Nothing>()
    object Loading : WorkResult<Nothing>()
}
