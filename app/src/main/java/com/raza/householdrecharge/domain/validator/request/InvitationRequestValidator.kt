package com.raza.householdrecharge.domain.validator.request

import javax.inject.Inject
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.request.HouseholdRequestError
import com.raza.householdrecharge.domain.error.request.RequestError
import com.raza.householdrecharge.domain.error.request.UserRequestError

class InvitationRequestValidator @Inject constructor(
) {

    fun validate(
        householdId: String,
        accountId: String
    ): Result<Unit, RequestError> {
        if (accountId.isEmpty()) {
            return Result.Failure(UserRequestError.AccountIdNotFound)
        }

        if (householdId.isEmpty()) {
            return Result.Failure(HouseholdRequestError.HouseholdNotFound)
        }

        return Result.Success(Unit)
    }
}