package com.safeway.nycschools.data

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val value: T) : Result<T>()
    sealed class Error(val e: Exception? = null) : Result<Nothing>() {
        object NoConnectivity : Error()
        object ServerIssue : Error()
        class UnknownError(e: Exception? = null) : Error(e)
    }
}