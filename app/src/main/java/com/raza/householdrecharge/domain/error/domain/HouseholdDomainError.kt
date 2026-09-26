package com.raza.householdrecharge.domain.error.domain

sealed interface HouseholdDomainError : AppError {
    data object HouseholdNotFound : HouseholdDomainError
}