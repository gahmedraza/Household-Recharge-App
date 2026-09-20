package com.raza.householdrecharge.domain.error

sealed class AccountRequestValidatorError {
    data object AccountNotFound : AccountRequestValidatorError()
    data object UnknownError : AccountRequestValidatorError()
}