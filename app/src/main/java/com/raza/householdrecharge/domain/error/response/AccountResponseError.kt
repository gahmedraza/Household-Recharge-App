package com.raza.householdrecharge.domain.error.response

import com.raza.householdrecharge.domain.error.usecase.AuthUseCaseError
import com.raza.householdrecharge.domain.error.usecase.ProfileUseCaseError

sealed interface AccountResponseError : ResponseError,
    AuthUseCaseError, ProfileUseCaseError {
    data object NoRecordFound : AccountResponseError
    data object DataParsingError : AccountResponseError
    data class AccountIdNotGenerated(val message: String) : AccountResponseError
    data class Unknown(val message: String) : AccountResponseError

}