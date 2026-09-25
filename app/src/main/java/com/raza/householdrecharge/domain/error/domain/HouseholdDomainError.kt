package com.raza.householdrecharge.domain.error.domain

sealed interface HouseholdDomainError {
    data object HouseholdNotFound : HouseholdDomainError //"household not found"
}