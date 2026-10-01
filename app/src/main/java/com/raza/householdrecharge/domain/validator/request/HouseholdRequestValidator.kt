package com.raza.householdrecharge.domain.validator.request

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.request.RequestError
import com.raza.householdrecharge.domain.validator.helpers.HouseholdRequestValidator2
import com.raza.householdrecharge.domain.validator.helpers.InvitationRequestValidator2
import com.raza.householdrecharge.domain.validator.helpers.MobileNumberRequestValidator2
import com.raza.householdrecharge.domain.validator.helpers.UserRequestValidator2
import javax.inject.Inject

class HouseholdRequestValidator @Inject constructor(
    private val userRequestValidator2: UserRequestValidator2,
    private val householdRequestValidator2: HouseholdRequestValidator2,
    private val invitationRequestValidator2: InvitationRequestValidator2
) {
    fun validate(
        accountId: String,
        authId: String
    ): Result<Unit, RequestError> {

        val result51 = userRequestValidator2.validateAccountId(
            accountId = accountId
        )

        if(result51 is Result.Failure) {
            return result51
        }

        val result52 = userRequestValidator2.validateAuthId(
            authId = authId
        )

        if(result52 is Result.Failure) {
            return result52
        }

        return Result.Success(Unit)
    }

    fun validate2(
        authId: String,
        invitationCode: String
    ): Result<Unit, RequestError> {

        val result51 = userRequestValidator2.validateAuthId(
            authId = authId
        )

        if(result51 is Result.Failure) {
            return result51
        }

        val result52 = invitationRequestValidator2.validateInvitationCode(
            invitationCode = invitationCode
        )

        if(result52 is Result.Failure) {
            return result52
        }

        return Result.Success(Unit)
    }

    fun validate(
        authId: String,
        householdId: String,
        invitationCode: String
    ): Result<Unit, RequestError> {

        val result51 = userRequestValidator2.validateAuthId(
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

        val result53 = invitationRequestValidator2.validateInvitationCode(
            invitationCode = invitationCode
        )

        if(result53 is Result.Failure) {
            return result53
        }

        return Result.Success(Unit)
    }
}