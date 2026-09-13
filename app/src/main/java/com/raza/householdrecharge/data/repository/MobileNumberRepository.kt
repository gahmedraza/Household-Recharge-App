package com.raza.householdrecharge.data.repository

import com.raza.householdrecharge.core.logging.log
import com.raza.householdrecharge.data.local.dao.MobileNumberDao
import com.raza.householdrecharge.data.remote.datasource.MobileNumberRemoteDataSource
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.local.entity.MobileNumberEntity
import com.raza.householdrecharge.data.remote.mapper.MobileNumberEntityMapper
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MobileNumberRepository @Inject constructor(
    private val mobileNumberRemoteDataSource: MobileNumberRemoteDataSource,
    private val mobileNumberDao: MobileNumberDao
) {

    suspend fun addMobileNumber(
        mobileNumberDto: MobileNumberDto

    ): Result<String, String> {
        val result = mobileNumberRemoteDataSource.addMobileNumber(mobileNumberDto)

        if(result is Result.Failure) {
            log(result.error.cleanString())
            return Result.Failure(result.error.cleanString())
        }

        val mobileNumberDto = (result as Result.Success).data

        val mobileNumberEntity = MobileNumberEntityMapper.map(mobileNumberDto)

        mobileNumberDao.upsertMobileNumber(mobileNumberEntity)

        return Result.Success("")
    }

    fun observeMobileNumbers(
    ): Flow<List<MobileNumberEntity>> {
        return mobileNumberDao.observeMobileNumbers()
    }

    suspend fun getAllMobileNumbers(
    ) {
        val result = mobileNumberRemoteDataSource.getAllMobileNumbers()

        if(result is Result.Failure) {
            log(result.error.cleanString())
        }

        val mobileNumberList = (result as Result.Success).data

        val mobileNumberEntityList = MobileNumberEntityMapper.map(mobileNumberList)

        mobileNumberDao.upsertMobileNumbers(mobileNumberEntityList)
    }
}