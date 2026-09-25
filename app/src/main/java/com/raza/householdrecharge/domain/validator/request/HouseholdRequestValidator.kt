package com.raza.householdrecharge.domain.validator.request

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.domain.UserDomainError.AccountIdNotFound
import com.raza.householdrecharge.domain.error.domain.UserDomainError.AuthIdNotFound
import com.raza.householdrecharge.domain.error.request.HouseholdRequestError
import com.raza.householdrecharge.domain.error.request.HouseholdRequestError.UserError
import javax.inject.Inject

class HouseholdRequestValidator @Inject constructor(
) {
    fun validate(
        accountId: String,
        authId: String
    ): Result<Unit, HouseholdRequestError> {

        if (accountId.isEmpty()) {
            return Result.Failure(UserError(AccountIdNotFound))
        }

        if (authId.isEmpty()) {
            return Result.Failure(UserError(AuthIdNotFound))
        }

        return Result.Success(Unit)
    }
}