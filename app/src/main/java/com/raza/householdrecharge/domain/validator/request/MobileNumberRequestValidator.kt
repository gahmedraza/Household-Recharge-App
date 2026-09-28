package com.raza.householdrecharge.domain.validator.request

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.request.HouseholdRequestError
import com.raza.householdrecharge.domain.error.request.MobileNumberRequestError
import com.raza.householdrecharge.domain.error.request.RequestError
import com.raza.householdrecharge.domain.error.request.UserRequestError
import javax.inject.Inject

class MobileNumberRequestValidator @Inject constructor(
) {

    fun validate(
        authId: String,
        householdId: String,
        mobileNumber: String
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

        return Result.Success(Unit)
    }
}