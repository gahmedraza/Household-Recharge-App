package com.raza.householdrecharge.presentation.common

object HouseholdNameValidator {

    fun validateHouseholdName(householdName: String, householdNameError: String): String {
        if (householdName.isEmpty()) {
            return "Household Name cannot be empty"
        }

        if (householdName.length < 4) {
            return "Household Name cannot be less than 4 characters"
        }

        if (householdName.length > 24) {
            return "Household Name cannot be more than 24 characters"
        }

        return householdNameError
    }
}