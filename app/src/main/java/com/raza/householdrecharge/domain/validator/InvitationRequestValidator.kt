package com.raza.householdrecharge.domain.validator

import com.raza.householdrecharge.domain.error.InvitationRequestValidatorError
import javax.inject.Inject
import com.raza.householdrecharge.core.result.Result

class InvitationRequestValidator @Inject constructor(
) {

    fun validate(
        householdId: String,
        accountId: String
    ): Result<Unit, InvitationRequestValidatorError> {
        if (accountId.isEmpty()) {
            return Result.Failure(InvitationRequestValidatorError.UserNotFound)
        }

        if (householdId.isEmpty()) {
            return Result.Failure(InvitationRequestValidatorError.HouseholdNotFound)
        }

        return Result.Success(Unit)
    }
}