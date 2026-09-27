package com.raza.householdrecharge.domain.error.domain

sealed interface UserDomainError : RequestError {
    data object AuthIdNotFound : UserDomainError
    data object AccountIdNotFound : UserDomainError
}