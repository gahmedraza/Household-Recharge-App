package com.raza.householdrecharge.presentation.common

object AccountNameValidator {

    fun validateAccountName(accountName: String, accountNameError: String): String {
        if (accountName.isEmpty()) {
            return "Account Name cannot be empty"
        }

        if (accountName.length < 4) {
            return "Account Name must contain at least 4 characters"
        }

        if (accountName.length > 24) {
            return "Account Name must cannot contain more than 24 characters"
        }

        return accountNameError
    }
}