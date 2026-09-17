package com.raza.householdrecharge.domain.error

sealed class AuthError {
    data object UserNotLoggedIn : AuthError()

    data object Unknown: AuthError()
}