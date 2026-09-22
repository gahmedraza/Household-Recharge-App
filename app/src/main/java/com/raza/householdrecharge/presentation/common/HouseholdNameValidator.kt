package com.raza.householdrecharge.presentation.common

object HouseholdNameValidator {

    fun validateHouseholdName(householdName: String, householdNameError: String): String {
        var householdNameError1 = householdNameError
        if (householdName.isEmpty()) {
            householdNameError1 = "Household Name cannot be empty"
        }

        if (householdName.length < 4) {
            householdNameError1 = "Household Name must contain at least 4 characters"
        }
        return householdNameError1
    }
}