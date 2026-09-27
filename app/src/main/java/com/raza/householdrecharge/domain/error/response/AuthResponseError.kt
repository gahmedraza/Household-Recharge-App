package com.raza.householdrecharge.domain.error.response

sealed class AuthResponseError : ResponseError {
    data object UserNotLoggedIn : AuthResponseError()

    data object UserNotCreated : AuthResponseError()

    data class Unknown(val message: String) : AuthResponseError()
}