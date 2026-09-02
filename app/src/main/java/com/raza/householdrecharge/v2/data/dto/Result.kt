package com.raza.householdrecharge.v2.data.dto

sealed class Result<T> {
    data class Success<T>(val s: T) : Result<T>()
    data class Failure<T>(val s: T) : Result<T>()
}