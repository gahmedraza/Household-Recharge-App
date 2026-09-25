package com.raza.householdrecharge.domain.validator.request

import com.raza.householdrecharge.domain.error.request.RechargeRequestError
import com.raza.householdrecharge.domain.error.request.RechargeRequestError.*
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.domain.HouseholdDomainError.*
import com.raza.householdrecharge.domain.error.domain.MobileNumberDomainError.*
import com.raza.householdrecharge.domain.error.domain.UserDomainError.*
import javax.inject.Inject

class RechargeRequestValidator @Inject constructor() {

    fun validateAddRechargeApiCall(
        authId: String,
        householdId: String,
        mobileNumber: String,
        mobileNumberId: String
    ): Result<Unit, RechargeRequestError> {

        if (authId.isEmpty()) {
            return Result.Failure(
                UserError(AuthIdNotFound)
            )
        }

        if (householdId.isEmpty()) {
            return Result.Failure(
                HouseholdError(HouseholdNotFound)
            )
        }

        if (mobileNumber.isEmpty()) {
            return Result.Failure(
                MobileNumberError(MobileNumberNotFound)
            )
        }

        if(mobileNumberId.isEmpty()) {
            return Result.Failure(
                MobileNumberError(MobileNumberIdNotFound)
            )
        }

        return Result.Success(Unit)
    }

    fun validateRechargeListingApiCall(
        authId: String,
        householdId: String,
        memberId: String,
        mobileNumber: String
    ): Result<Unit, RechargeRequestError> {

        if (authId.isEmpty()) {
            return Result.Failure(
                UserError(AuthIdNotFound)
            )
        }

        if (householdId.isEmpty()) {
            return Result.Failure(
                HouseholdError(HouseholdNotFound)
            )
        }

        return Result.Success(Unit)
    }
}