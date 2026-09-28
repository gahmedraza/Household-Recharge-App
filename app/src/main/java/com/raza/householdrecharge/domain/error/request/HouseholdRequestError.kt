package com.raza.householdrecharge.domain.error.request

sealed interface HouseholdRequestError : RequestError {
    data object HouseholdNotFound : HouseholdRequestError
}