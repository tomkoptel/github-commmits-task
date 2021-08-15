package com.olderwold.jlabs.github.feature.details.ui

sealed class SafeResult<T : Any> {
    data class Ok<T : Any>(val data: T) : SafeResult<T>()
    data class Error<T : Any>(val throwable: Throwable) : SafeResult<T>()
}

fun <T : Any> Result<T>.toSafeResult(): SafeResult<T> {
    return fold(onSuccess = {
        SafeResult.Ok(it)
    }, onFailure = {
        SafeResult.Error(it)
    })
}
