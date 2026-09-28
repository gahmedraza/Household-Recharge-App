package com.raza.householdrecharge.domain.validator.request

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.request.RequestError
import com.raza.householdrecharge.domain.error.request.UserRequestError
import javax.inject.Inject

class AccountRequestValidator @Inject constructor(
) {

    fun validate(
        accountId: String
    ): Result<Unit, RequestError> {

        if (accountId.isEmpty()) {
            return Result.Failure(UserRequestError.AccountIdNotFound)
        }

        return Result.Success(Unit)
    }
}