package com.raza.householdrecharge.domain.validator

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.MobileNumberRequestValidationError
import javax.inject.Inject

class MobileNumberRequestValidator @Inject constructor(
) {

    fun validate(
        userId: String,
        householdId: String,
        mobileNumber: String
    ): Result<Unit, MobileNumberRequestValidationError> {

        if (userId.isEmpty()) {
            return Result.Failure(
                MobileNumberRequestValidationError.UserNotFound
            )
        }

        if (householdId.isEmpty()) {
            return Result.Failure(
                MobileNumberRequestValidationError.HouseholdNotFound
            )
        }

        if (mobileNumber.isEmpty()) {
            return Result.Failure(
                MobileNumberRequestValidationError.MobileNumberEmpty
            )
        }

        return Result.Success(Unit)
    }
}