package com.raza.householdrecharge.domain.validator

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.AddRechargeValidationError
import com.raza.householdrecharge.domain.error.MobileNumberValidationError

class MobileNumberValidator {

    fun validate(
        userId: String,
        householdId: String,
        mobileNumber: String
    ): Result<Unit, MobileNumberValidationError> {

        if (userId.isEmpty()) {
            return Result.Failure(
                MobileNumberValidationError.UserNotFound
            )
        }

        if (householdId.isEmpty()) {
            return Result.Failure(
                MobileNumberValidationError.HouseholdNotFound
            )
        }

        if (mobileNumber.isEmpty()) {
            return Result.Failure(
                MobileNumberValidationError.MobileNumberEmpty
            )
        }

        return Result.Success(Unit)
    }
}