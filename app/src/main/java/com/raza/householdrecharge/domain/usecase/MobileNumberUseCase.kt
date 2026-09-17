package com.raza.householdrecharge.domain.usecase

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.local.entity.MobileNumberEntity
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto
import com.raza.householdrecharge.data.repository.MobileNumberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MobileNumberUseCase @Inject constructor(
    private val mobileNumberRepository: MobileNumberRepository
) {

    suspend fun addMobileNumber(
        mobileNumberDto: MobileNumberDto

    ): Result<String, String> {

        return mobileNumberRepository.addMobileNumber(
            mobileNumberDto
        )
    }

    fun observeMobileNumbers(
    ): Flow<List<MobileNumberEntity>> {

        return mobileNumberRepository.observeMobileNumbers()
    }

    suspend fun getAllMobileNumbers(
    ) {

        return mobileNumberRepository.getAllMobileNumbers()
    }

    suspend fun updateMobileNumber(
        mobileNumberId: String,
        rechargeId: String
    ): Result<Boolean, String> {

        return mobileNumberRepository.updateMobileNumber(
            mobileNumberId,
            rechargeId
        )
    }
}