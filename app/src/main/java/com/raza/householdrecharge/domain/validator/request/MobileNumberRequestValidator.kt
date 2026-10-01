package com.raza.householdrecharge.domain.validator.request

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.request.RequestError
import com.raza.householdrecharge.domain.validator.helpers.HouseholdRequestValidator2
import com.raza.householdrecharge.domain.validator.helpers.MobileNumberRequestValidator2
import com.raza.householdrecharge.domain.validator.helpers.UserRequestValidator2
import javax.inject.Inject

class MobileNumberRequestValidator @Inject constructor(
    private val userRequestValidator2: UserRequestValidator2,
    private val householdRequestValidator2: HouseholdRequestValidator2,
    private val mobileNumberRequestValidator2: MobileNumberRequestValidator2
) {

    fun validate(
        authId: String,
        householdId: String,
        mobileNumber: String
    ): Result<Unit, RequestError> {

        val result51 = validate(
            authId = authId,
            householdId = householdId
        )

        if(result51 is Result.Failure) {
            return result51
        }

        val result52 = mobileNumberRequestValidator2.validateMobileNumber(
            mobileNumber = mobileNumber
        )

        if(result52 is Result.Failure) {
            return result52
        }

        return Result.Success(Unit)
    }

    fun validate(
        authId: String,
        householdId: String,
    ): Result<Unit, RequestError> {

        val result51 =  userRequestValidator2.validateAuthId(
            authId = authId
        )

        if(result51 is Result.Failure) {
            return result51
        }

        val result52 = householdRequestValidator2.validateHouseholdId(
            householdId = householdId
        )

        if(result52 is Result.Failure) {
            return result52
        }

        return Result.Success(Unit)
    }
}