package com.raza.householdrecharge.domain.error

sealed class HouseholdRequestValidationError {
    data object UserNotFound : HouseholdRequestValidationError()
    data object AccountNotFound : HouseholdRequestValidationError()
    data object UnknownError : HouseholdRequestValidationError()
}