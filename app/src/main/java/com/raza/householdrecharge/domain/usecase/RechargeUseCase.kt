package com.raza.householdrecharge.domain.usecase

import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.RechargeDto
import com.raza.householdrecharge.data.repository.MobileNumberRepository
import com.raza.householdrecharge.data.repository.RechargeRepository
import com.raza.householdrecharge.domain.model.Recharge
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RechargeUseCase @Inject constructor(
    private val rechargeRepository: RechargeRepository,
    private val mobileNumberRepository: MobileNumberRepository
) {

    suspend fun addRechargeAndUpdateMobileNumber(
        rechargeDto: RechargeDto,
        mobileNumberId: String
    ): Result<String, String> {

        val result = rechargeRepository.addRecharge(
            rechargeDto = rechargeDto
        )

        if(result is Result.Failure) {
            Logger.log(result.error.cleanString())
            return Result.Failure(result.error.cleanString())
        }

        val rechargeId = (result as Result.Success).data

        val result2 = mobileNumberRepository.updateMobileNumber(
            mobileNumberId = mobileNumberId,
            rechargeId = rechargeId
        )

        if(result2 is Result.Failure) {
            Logger.log(result2.error)
            return Result.Failure(result2.error.cleanString())
        }

        return Result.Success("")
    }

    /**
     * Transit Method
     * No additional code
     */
    fun observeRecharges(
    ): Flow<List<Recharge>> {

        return rechargeRepository.observeRecharges()
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun getAllRecharges(

    ) {
        return rechargeRepository.getAllRecharges()
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun queryDatabase() {

        return rechargeRepository.queryDatabase()
    }
}