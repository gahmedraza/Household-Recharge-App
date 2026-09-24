package com.raza.householdrecharge.domain.error

sealed class AccountError {
    data object AccountNotFound: AccountError()
    data object AccountEmpty: AccountError()

    data object InvalidAccountRecord: AccountError()

    data class AccountIdNotGenerated(val message: String): AccountError()

    data object AccountNotEligibleToJoin: AccountError()
    data class Unknown(val message: String): AccountError()
}