package com.raza.householdrecharge.domain.error.response

sealed interface AccountResponseError : ResponseError {
    data object AccountEmptyInRemote : AccountResponseError
    data object AccountParsingError : AccountResponseError
    data class AccountIdNotGenerated(val message: String) : AccountResponseError
    data class Unknown(val message: String) : AccountResponseError

}