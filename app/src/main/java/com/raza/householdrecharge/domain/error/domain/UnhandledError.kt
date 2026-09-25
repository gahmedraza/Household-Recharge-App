package com.raza.householdrecharge.domain.error.domain

sealed interface UnhandledError {
    data object UnknownError : UnhandledError
}