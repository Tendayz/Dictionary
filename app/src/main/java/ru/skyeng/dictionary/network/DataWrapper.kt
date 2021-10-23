package ru.skyeng.dictionary.network

data class DataWrapper<T>(
    val status: Status,
    val data: List<T>? = null
)

sealed class Status {

    object Loading : Status()

    object Success : Status()

    data class Error(val throwable: Throwable?) : Status()
}
