package com.raza.householdrecharge.domain.validator.request

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.request.RequestError
import com.raza.householdrecharge.domain.validator.helpers.HouseholdRequestValidator2
import com.raza.householdrecharge.domain.validator.helpers.UserRequestValidator2
import javax.inject.Inject

class SplashRequestValidator @Inject constructor(
    private val accountRequestValidator2: UserRequestValidator2,
    private val householdRequestValidator2: HouseholdRequestValidator2
) {

    fun validate(
        accountId: String
    ): Result<Unit, RequestError> {

        return accountRequestValidator2.validateAccountId(
            accountId = accountId
        )
    }

    fun validate2(
        householdId: String,
    ): Result<Unit, RequestError> {

        return householdRequestValidator2.validateHouseholdId(
            householdId = householdId
        )
    }
}