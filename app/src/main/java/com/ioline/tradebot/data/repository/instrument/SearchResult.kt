package com.ioline.tradebot.data.repository.instrument

sealed class SearchResult<out R> {
    data class Success<out T>(val data: T) : SearchResult<T>()
    data class Error(val throwable: Throwable) : SearchResult<Nothing>()
    object NoResult : SearchResult<Nothing>()
}
