package com.raza.householdrecharge.domain.error.domain

sealed interface UnhandledError : RequestError {
    data object UnknownError : UnhandledError
}//not used by request