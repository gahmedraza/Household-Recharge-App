package com.raza.householdrecharge.domain.validator.request

import com.raza.householdrecharge.domain.error.request.InvitationRequestError
import com.raza.householdrecharge.domain.error.request.InvitationRequestError.*
import javax.inject.Inject
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.domain.HouseholdDomainError.*
import com.raza.householdrecharge.domain.error.domain.UserDomainError.*

class InvitationRequestValidator @Inject constructor(
) {

    fun validate(
        householdId: String,
        accountId: String
    ): Result<Unit, InvitationRequestError> {
        if (accountId.isEmpty()) {
            return Result.Failure(UserError(AccountIdNotFound))
        }

        if (householdId.isEmpty()) {
            return Result.Failure(HouseholdError(HouseholdNotFound))
        }

        return Result.Success(Unit)
    }
}