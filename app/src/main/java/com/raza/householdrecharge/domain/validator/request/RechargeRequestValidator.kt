package com.raza.householdrecharge.domain.validator.request

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.request.HouseholdRequestError
import com.raza.householdrecharge.domain.error.request.MobileNumberRequestError
import com.raza.householdrecharge.domain.error.request.RequestError
import com.raza.householdrecharge.domain.error.request.UserRequestError
import javax.inject.Inject

class RechargeRequestValidator @Inject constructor() {

    fun validateAddRechargeApiCall(
        authId: String,
        householdId: String,
        mobileNumber: String,
        mobileNumberId: String
    ): Result<Unit, RequestError> {

        if (authId.isEmpty()) {
            return Result.Failure(UserRequestError.AuthIdNotFound)
        }

        if (householdId.isEmpty()) {
            return Result.Failure(HouseholdRequestError.HouseholdNotFound)
        }

        if (mobileNumber.isEmpty()) {
            return Result.Failure(MobileNumberRequestError.MobileNumberNotFound)
        }

        if(mobileNumberId.isEmpty()) {
            return Result.Failure(MobileNumberRequestError.MobileNumberIdNotFound)
        }

        return Result.Success(Unit)
    }

    fun validateRechargeListingApiCall(
        authId: String,
        householdId: String,
        memberId: String,
        mobileNumber: String
    ): Result<Unit, RequestError> {

        if (authId.isEmpty()) {
            return Result.Failure(UserRequestError.AuthIdNotFound)
        }

        if (householdId.isEmpty()) {
            return Result.Failure(HouseholdRequestError.HouseholdNotFound)
        }

        return Result.Success(Unit)
    }
}