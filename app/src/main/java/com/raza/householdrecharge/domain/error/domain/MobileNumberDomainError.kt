package com.raza.householdrecharge.domain.error.domain

sealed interface MobileNumberDomainError : RequestError {
    data object MobileNumberNotFound: MobileNumberDomainError
    data object MobileNumberIdNotFound: MobileNumberDomainError
}