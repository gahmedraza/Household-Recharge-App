package com.raza.householdrecharge.domain.validator.helpers

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.request.HouseholdRequestError
import com.raza.householdrecharge.domain.error.request.RequestError
import javax.inject.Inject

class HouseholdRequestValidator2 @Inject constructor(
) {

    fun validateHouseholdId(
        householdId: String,
    ): Result<Unit, RequestError> {

        if (householdId.isEmpty()) {
            return Result.Failure(HouseholdRequestError.HouseholdNotFound)
        }

        return Result.Success(Unit)
    }
}