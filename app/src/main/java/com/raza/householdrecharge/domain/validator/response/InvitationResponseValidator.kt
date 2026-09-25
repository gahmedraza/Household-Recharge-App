package com.raza.householdrecharge.domain.validator.response

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.InvitationDto
import javax.inject.Inject

class InvitationResponseValidator @Inject constructor(
) {

    fun validate(
        invitationDto: InvitationDto,
        householdId: String
    ): Result<Unit, String> {

        if(invitationDto.status != "pending") {
            return Result.Failure("invitation has already been used")
        }

        if(invitationDto.householdId != householdId) {
            return Result.Failure("invalid invitation")
        }

        if(invitationDto.expiresAt.toLong() < System.currentTimeMillis()) {
            return Result.Failure("invitation has expired")
        }

        return Result.Success(Unit)
    }
}