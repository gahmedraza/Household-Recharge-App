package com.raza.householdrecharge.domain.validator.helpers

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.request.MobileNumberRequestError
import com.raza.householdrecharge.domain.error.request.RequestError
import javax.inject.Inject

class MobileNumberRequestValidator2 @Inject constructor(
) {

    fun validateMobileNumber(
        mobileNumber: String
    ): Result<Unit, RequestError> {

        if (mobileNumber.isEmpty()) {
            return Result.Failure(MobileNumberRequestError.MobileNumberNotFound)
        }

        return Result.Success(Unit)
    }

    fun validateMobileNumberId(
        mobileNumberId: String
    ): Result<Unit, RequestError> {

        if(mobileNumberId.isEmpty()) {
            return Result.Failure(MobileNumberRequestError.MobileNumberIdNotFound)
        }

        return Result.Success(Unit)
    }
}