package com.raza.householdrecharge.core.result

sealed class Result<out T, out E> {
    data class Success<T>(val s: T) : Result<T, Nothing>()
    data class Failure<E>(val s: E) : Result<Nothing, E>()
}