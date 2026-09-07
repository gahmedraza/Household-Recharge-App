package com.raza.householdrecharge.domain.error

sealed class MobileNumberValidationError {
    data object UserNotFound : MobileNumberValidationError()//"user not found"

    data object HouseholdNotFound : MobileNumberValidationError()//"household not found"
    data object MobileNumberEmpty : MobileNumberValidationError()//"No mobile is empty"
}