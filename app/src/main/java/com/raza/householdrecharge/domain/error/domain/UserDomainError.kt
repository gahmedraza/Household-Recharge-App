package com.raza.householdrecharge.domain.error.domain

sealed interface UserDomainError {
    data object AuthIdNotFound: UserDomainError //"user not found"
    data object AccountIdNotFound: UserDomainError //"user not found"
}