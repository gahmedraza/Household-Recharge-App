package com.raza.householdrecharge.domain.error.response

import com.raza.householdrecharge.domain.error.usecase.AuthUseCaseError

sealed class AuthResponseError : ResponseError, AuthUseCaseError {
    data object UserNotLoggedIn : AuthResponseError()

    data object UserNotCreated : AuthResponseError()

    data class Unknown(val message: String) : AuthResponseError()
}