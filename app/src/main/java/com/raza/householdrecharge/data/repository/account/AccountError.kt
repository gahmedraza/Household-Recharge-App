package com.raza.householdrecharge.data.repository.account

sealed class AccountError {
    data object AccountNotFound: AccountError()
    data object AccountEmpty: AccountError()
    data object Unknown: AccountError()
}