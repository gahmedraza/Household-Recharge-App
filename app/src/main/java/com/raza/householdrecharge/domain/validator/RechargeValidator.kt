package com.raza.householdrecharge.domain.validator

import com.raza.householdrecharge.common.log
import com.raza.householdrecharge.domain.error.AddRechargeValidationError
import com.raza.householdrecharge.core.result.Result

class RechargeValidator {

    fun validateAddRechargeApiCall(
        userId: String,
        householdId: String,
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

        if (mobileNumber.isEmpty()) {
            return Result.Failure(
                AddRechargeValidationError.MobileNumberNotFound
            )
        }

        return Result.Success(Unit)
    }

    fun validateRechargeListingApiCall(
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

        return Result.Success(Unit)
    }
}