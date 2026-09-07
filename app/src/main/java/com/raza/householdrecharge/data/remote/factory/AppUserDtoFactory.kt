package com.raza.householdrecharge.data.remote.factory

import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import kotlinx.coroutines.flow.first

class AppUserDtoFactory(
    private val sessionManager: SessionManager
) {

    suspend fun create(
        memberId: String,
        mobileNumber: String
    ): AppUserDto {

        return AppUserDto(
            authId = sessionManager.accountId.first(),
            householdId = sessionManager.householdId.first(),
            memberId = memberId,
            mobileNumber = mobileNumber
        )
    }
}