package com.raza.householdrecharge.domain.validator.request

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.request.RequestError
import com.raza.householdrecharge.domain.validator.helpers.UserRequestValidator2
import javax.inject.Inject

class AccountRequestValidator @Inject constructor(
    private val accountRequestValidator2: UserRequestValidator2,
) {

    fun validate(
        accountId: String
    ): Result<Unit, RequestError> {

        val result51 = accountRequestValidator2.validateAccountId(
            accountId = accountId
        )

        if (result51 is Result.Failure) {
            return result51
        }

        return Result.Success(Unit)
    }
}