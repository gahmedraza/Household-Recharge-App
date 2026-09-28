package com.raza.householdrecharge.domain.error.response

sealed interface AccountResponseError : ResponseError {
    data object NoRecordFound : AccountResponseError
    data object DataParsingError : AccountResponseError
    data class AccountIdNotGenerated(val message: String) : AccountResponseError
    data class Unknown(val message: String) : AccountResponseError

}