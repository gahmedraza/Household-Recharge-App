package com.raza.householdrecharge.domain.validator

import com.raza.householdrecharge.common.log
import com.raza.householdrecharge.domain.error.AddRechargeValidationError
import com.raza.householdrecharge.core.result.Result

class AddRechargeValidator {

    fun validate(
        userId: String,
        householdId: String,
        memberId: String,
        mobileNumber: String
    ): Result<Unit, AddRechargeValidationError> {

        if (userId.isEmpty()) {
            return Result.Failure(
                AddRechargeValidationError.UserNotFound
            )
        }

        if (householdId.isEmpty()) {
            return Result.Failure(
                AddRechargeValidationError.HouseholdNotFound
            )
        }

        if (memberId.isEmpty()) {
            log("memberId: $memberId")
            return Result.Failure(
                AddRechargeValidationError.MemberNotFound
            )
        }

        if (mobileNumber.isEmpty()) {
            return Result.Failure(
                AddRechargeValidationError.MobileNumberNotFound
            )
        }

        return Result.Success(Unit)
    }
}