package ru.skyeng.dictionary.network

data class BaseResponse<T>(
    val data: List<T>?,
    val throwable: Throwable? = null,
)
