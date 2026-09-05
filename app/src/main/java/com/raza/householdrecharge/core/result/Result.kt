package com.raza.householdrecharge.core.result

sealed class Result<out T, out E> {
    data class Success<T>(val data: T) : Result<T, Nothing>()
    data class Failure<E>(val error: E) : Result<Nothing, E>()
}