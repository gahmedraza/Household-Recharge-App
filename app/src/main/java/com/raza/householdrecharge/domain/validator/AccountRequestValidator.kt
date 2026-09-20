package com.raza.householdrecharge.domain.validator

import com.raza.householdrecharge.domain.error.AccountRequestValidatorError
import javax.inject.Inject
import com.raza.householdrecharge.core.result.Result

class AccountRequestValidator @Inject constructor(
) {

    fun validate(
        accountId: String
    ): Result<Unit, AccountRequestValidatorError> {

        if (accountId.isEmpty()) {
            return Result.Failure(AccountRequestValidatorError.AccountNotFound)
        }

        return Result.Success(Unit)
    }
}