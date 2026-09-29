package com.raza.householdrecharge.data.repository

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.local.dao.MobileNumberDao
import com.raza.householdrecharge.data.remote.datasource.MobileNumberRemoteDataSource
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto
import com.raza.householdrecharge.data.remote.mapper.mobilenumber.MobileNumberEntityMapper
import com.raza.householdrecharge.data.remote.mapper.mobilenumber.MobileNumberMapper
import com.raza.householdrecharge.domain.error.response.MobileNumberResponseError
import com.raza.householdrecharge.domain.model.MobileNumber
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MobileNumberRepository @Inject constructor(
    private val mobileNumberRemoteDataSource: MobileNumberRemoteDataSource,
    private val mobileNumberDao: MobileNumberDao
) {

    suspend fun addMobileNumber(
        mobileNumberDto: MobileNumberDto

    ): Result<String, MobileNumberResponseError> {
        val result = mobileNumberRemoteDataSource.addMobileNumber(mobileNumberDto)

        if(result is Result.Failure) {
            //Logger.log(result.error.cleanString()) //todo
            //return Result.Failure(result.error.cleanString())
            return result
        }

        val mobileNumberDto = (result as Result.Success).data

        val mobileNumberEntity = MobileNumberEntityMapper.map(mobileNumberDto)

        mobileNumberDao.upsertMobileNumber(mobileNumberEntity)

        return Result.Success("")
    }

    fun observeMobileNumbers(
    ): Flow<List<MobileNumber>> {
        return mobileNumberDao.observeMobileNumbers().map { mobileNumberEntityList ->
            MobileNumberMapper.map2(mobileNumberEntityList)
        }
    }

    //TODO add error propagation
    //event bus to post messages to the queue without propagation
    //chatgpt
    suspend fun getAllMobileNumbers(
    ) {
        val result = mobileNumberRemoteDataSource.getAllMobileNumbers()

        if(result is Result.Failure) {
            //Logger.log(result.error.cleanString())
        }

        val mobileNumberList = (result as Result.Success).data

        val mobileNumberEntityList = MobileNumberEntityMapper.map(mobileNumberList)

        mobileNumberDao.upsertMobileNumbers(mobileNumberEntityList)
    }

    suspend fun updateMobileNumber(
        mobileNumberId: String,
        rechargeId: String
    ): Result<Boolean, MobileNumberResponseError> {

        val result = mobileNumberRemoteDataSource.updateMobileNumber(mobileNumberId, rechargeId)

        if(result is Result.Failure) {
            //return Result.Failure(result.error.cleanString())
            return result
        }

        val isSuccess = (result as Result.Success).data

        /*if(!isSuccess) {
            return Result.Failure("failure")
        }*/ //todo such a result in never returned from the data source

        mobileNumberDao.updateMobileNumber(mobileNumberId, rechargeId)

        return Result.Success(true)
    }
}