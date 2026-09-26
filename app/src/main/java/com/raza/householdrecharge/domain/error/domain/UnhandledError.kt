package com.raza.householdrecharge.domain.error.domain

sealed interface UnhandledError : AppError {
    data object UnknownError : UnhandledError
}//not used by request