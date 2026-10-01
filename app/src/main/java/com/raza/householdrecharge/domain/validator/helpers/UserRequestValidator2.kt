package com.raza.householdrecharge.domain.validator.helpers

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.request.RequestError
import com.raza.householdrecharge.domain.error.request.UserRequestError
import javax.inject.Inject

class UserRequestValidator2 @Inject constructor(
) {

    fun validateAuthId(
        authId: String
    ): Result<Unit, RequestError> {

        if (authId.isEmpty()) {
            return Result.Failure(UserRequestError.AuthIdNotFound)
        }

        return Result.Success(Unit)
    }

    fun validateAccountId(
        accountId: String
    ): Result<Unit, RequestError> {

        if (accountId.isEmpty()) {
            return Result.Failure(UserRequestError.AccountIdNotFound)
        }

        return Result.Success(Unit)
    }
}