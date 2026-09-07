package com.raza.householdrecharge.data.remote.factory

import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.data.remote.dto.RechargeDto
import kotlinx.coroutines.flow.first

class RechargeDtoFactory(
    private val sessionManager: SessionManager
) {

    suspend fun create(
        rechargeAmount: Int = 0,
        rechargeDate: Long = 0L,
        expiryDate: Long = 0L,
        rechargedBy: String = "",
        mobileNumber: Int = 0
    ): RechargeDto {

        return RechargeDto(
            rechargeAmount = rechargeAmount,
            rechargeDate = rechargeDate,
            expiryDate = expiryDate,
            rechargedBy = rechargedBy,
            mobileNumber = mobileNumber,
            accountId = sessionManager.accountId.first(),
            householdId = sessionManager.householdId.first(),
        )
    }
}