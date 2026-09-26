package com.raza.householdrecharge.domain.error.domain

sealed interface UserDomainError : AppError {
    data object AuthIdNotFound : UserDomainError
    data object AccountIdNotFound : UserDomainError
}