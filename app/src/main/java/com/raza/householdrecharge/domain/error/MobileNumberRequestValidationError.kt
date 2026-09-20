package com.raza.householdrecharge.domain.error

sealed class MobileNumberRequestValidationError {
    data object UserNotFound : MobileNumberRequestValidationError()//"user not found"

    data object HouseholdNotFound : MobileNumberRequestValidationError()//"household not found"
    data object MobileNumberEmpty : MobileNumberRequestValidationError()//"No mobile is empty"
}