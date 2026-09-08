package com.raza.householdrecharge.data.remote.factory

import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto
import kotlinx.coroutines.flow.first

class MobileNumberDtoFactory(
    private val sessionManager: SessionManager
) {

    suspend fun create(
        mobileNumber: Long = 0
    ): MobileNumberDto {

        return MobileNumberDto(
            mobileNumber = mobileNumber,
            accountId = sessionManager.authId.first(),
            householdId = sessionManager.householdId.first(),
        )
    }
}