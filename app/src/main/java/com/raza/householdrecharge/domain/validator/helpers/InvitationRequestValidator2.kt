package com.raza.householdrecharge.domain.validator.helpers

import javax.inject.Inject
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.request.InvitationRequestError
import com.raza.householdrecharge.domain.error.request.RequestError

class InvitationRequestValidator2 @Inject constructor(
) {

    fun validateInvitationCode(
        invitationCode: String
    ): Result<Unit, RequestError> {

        if (invitationCode.isEmpty()) {
            return Result.Failure(InvitationRequestError.InvitationNotFound)
        }

        return Result.Success(Unit)
    }
}