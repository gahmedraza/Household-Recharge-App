package com.raza.householdrecharge.domain.validator.response

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.InvitationDto
import com.raza.householdrecharge.domain.error.response.InvitationResponseError
import com.raza.householdrecharge.domain.model.InvitationStatus
import javax.inject.Inject

class InvitationResponseValidator @Inject constructor(
) {

    fun validate(
        invitationDto: InvitationDto
    ): Result<Unit, InvitationResponseError> {

        if (invitationDto.status != InvitationStatus.PENDING.description) {
            return Result.Failure(
                InvitationResponseError.InvitationAlreadyUsed
            )
        }

        if (invitationDto.expiresAt.toLong() < System.currentTimeMillis()) {
            return Result.Failure(
                InvitationResponseError.InvitationExpired
            )
        }

        return Result.Success(Unit)
    }

    fun validate(
        invitationDto: InvitationDto,
        householdId: String
    ): Result<Unit, InvitationResponseError> {
        if (invitationDto.householdId != householdId) {
            return Result.Failure(
                InvitationResponseError.InvitationHouseholdMismatch
            )
        }

        return Result.Success(Unit)
    }
}