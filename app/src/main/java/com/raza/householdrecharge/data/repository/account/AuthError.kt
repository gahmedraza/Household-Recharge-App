package com.raza.householdrecharge.data.repository.account

sealed class AuthError {
    data object UserNotLoggedIn : AuthError()

    data object Unknown: AuthError()
}