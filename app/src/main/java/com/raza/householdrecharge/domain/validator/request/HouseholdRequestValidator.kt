package com.raza.householdrecharge.domain.validator.request

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.request.RequestError
import com.raza.householdrecharge.domain.error.request.UserRequestError.AccountIdNotFound
import com.raza.householdrecharge.domain.error.request.UserRequestError.AuthIdNotFound
import javax.inject.Inject

class HouseholdRequestValidator @Inject constructor(
) {
    fun validate(
        accountId: String,
        authId: String
    ): Result<Unit, RequestError> {

        if (accountId.isEmpty()) {
            return Result.Failure(AccountIdNotFound)
        }

        if (authId.isEmpty()) {
            return Result.Failure(AuthIdNotFound)
        }

        return Result.Success(Unit)
    }
}