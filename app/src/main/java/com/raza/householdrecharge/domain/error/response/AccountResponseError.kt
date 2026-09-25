package com.raza.householdrecharge.domain.error.response

sealed class AccountResponseError {
    data object AccountNotFound: AccountResponseError()
    data object AccountEmpty: AccountResponseError()

    data object InvalidAccountRecord: AccountResponseError()

    data class AccountIdNotGenerated(val message: String): AccountResponseError()

    data object AccountNotEligibleToJoin: AccountResponseError()
    data class Unknown(val message: String): AccountResponseError()
}