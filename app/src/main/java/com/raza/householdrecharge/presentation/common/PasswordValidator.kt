package com.raza.householdrecharge.presentation.common

object PasswordValidator {

    fun validatePassword(password: String, passwordError: String): String {
        var passwordError1 = passwordError
        if(password.length < 8) {
            passwordError1 = "Password must contain at least 8 characters"
        }
        return passwordError1
    }
}