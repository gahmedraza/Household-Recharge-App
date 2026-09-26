package com.raza.householdrecharge.domain.error.domain

sealed interface MobileNumberDomainError : AppError {
    data object MobileNumberNotFound: MobileNumberDomainError
    data object MobileNumberIdNotFound: MobileNumberDomainError
}