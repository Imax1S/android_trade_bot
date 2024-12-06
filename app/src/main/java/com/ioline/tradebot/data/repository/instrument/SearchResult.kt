package com.ioline.tradebot.data.repository.instrument

sealed class SearchResult<out R> {
    data class Success<out T>(val data: T?) : SearchResult<T>()
    data class Error(val errorMessage: String) : SearchResult<Nothing>()
    data object NoResult : SearchResult<Nothing>()
}
