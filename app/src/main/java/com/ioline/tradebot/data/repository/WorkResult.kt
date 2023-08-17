package com.ioline.tradebot.data.repository

sealed class WorkResult<out R> {
    data class Success<out T>(val data: T) : WorkResult<T>()
    data class Error(val exceptions: Exception) : WorkResult<Nothing>()
    object Loading : WorkResult<Nothing>()
}
