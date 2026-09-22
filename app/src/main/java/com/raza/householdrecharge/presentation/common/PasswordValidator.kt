package com.raza.householdrecharge.presentation.common

object PasswordValidator {

    fun validatePassword(password: String, passwordError: String): String {
        if(password.isEmpty()) {
            return "Password cannot be empty"
        }

        if(password.length < 8) {
            return "Password cannot be less than 8 characters"
        }

        if(password.length > 24) {
            return "Password cannot be more than 24 characters"
        }

        return passwordError
    }
}