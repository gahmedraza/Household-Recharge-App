package com.raza.householdrecharge.domain.error.request

sealed interface UserRequestError : RequestError {
    data object AuthIdNotFound : UserRequestError
    data object AccountIdNotFound : UserRequestError
}