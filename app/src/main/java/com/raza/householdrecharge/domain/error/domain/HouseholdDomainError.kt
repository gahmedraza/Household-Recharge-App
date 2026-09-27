package com.raza.householdrecharge.domain.error.domain

sealed interface HouseholdDomainError : RequestError {
    data object HouseholdNotFound : HouseholdDomainError
}