package com.raza.householdrecharge.domain.validator.request

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.domain.HouseholdDomainError.*
import com.raza.householdrecharge.domain.error.domain.MobileNumberDomainError.*
import com.raza.householdrecharge.domain.error.domain.UserDomainError.*
import com.raza.householdrecharge.domain.error.request.MobileNumberRequestError
import com.raza.householdrecharge.domain.error.request.MobileNumberRequestError.*
import javax.inject.Inject

class MobileNumberRequestValidator @Inject constructor(
) {

    fun validate(
        authId: String,
        householdId: String,
        mobileNumber: String
    ): Result<Unit, MobileNumberRequestError> {

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

        return Result.Success(Unit)
    }
}