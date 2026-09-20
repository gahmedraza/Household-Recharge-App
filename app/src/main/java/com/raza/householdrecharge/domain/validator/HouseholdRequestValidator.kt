package com.raza.householdrecharge.domain.validator

import com.raza.householdrecharge.domain.error.HouseholdRequestValidationError
import javax.inject.Inject
import com.raza.householdrecharge.core.result.Result

class HouseholdRequestValidator @Inject constructor(
) {
    fun validate(
        accountId: String,
        authId: String
    ): Result<Unit, HouseholdRequestValidationError> {

        if (accountId.isEmpty()) {
            return Result.Failure(HouseholdRequestValidationError.UserNotFound)
        }

        if (authId.isEmpty()) {
            return Result.Failure(HouseholdRequestValidationError.AccountNotFound)
        }

        return Result.Success(Unit)
    }
}