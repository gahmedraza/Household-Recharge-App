package com.raza.householdrecharge.presentation.common

object AccountNameValidator {

    fun validateAccountName(accountName: String, accountNameError: String): String {
        var accountNameError1 = accountNameError
        if (accountName.isEmpty()) {
            accountNameError1 = "Account Name cannot be empty"
        }

        if (accountName.length < 4) {
            accountNameError1 = "Account Name must contain at least 4 characters"
        }
        return accountNameError1
    }
}