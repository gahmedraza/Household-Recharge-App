package com.raza.householdrecharge.domain.error.response

sealed class AuthResponseError {
    data object UserNotLoggedIn : AuthResponseError()

    data object Unknown: AuthResponseError()
}