package com.raza.householdrecharge.presentation.common

import javax.inject.Inject

class PasswordValidator @Inject constructor() {

    fun validate(password: String, passwordError: String): String {
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