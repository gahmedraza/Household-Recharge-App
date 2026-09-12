package com.raza.householdrecharge.data.repository

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

        return mobileNumberRemoteDataSource.addMobileNumber(mobileNumberDto)
    }

    fun observeMobileNumbers(
    ): Flow<List<MobileNumberEntity>> {
        return mobileNumberDao.observeMobileNumbers()
    }

    suspend fun getAllMobileNumbers(
    ): Result<List<MobileNumberDto>, String> {

        return mobileNumberRemoteDataSource.getAllMobileNumbers()
    }

    suspend fun getAllMobileNumbers2(
    ): Result<List<MobileNumberDto>, String> {
        val result = mobileNumberRemoteDataSource.getAllMobileNumbers()

        if(result is Result.Failure) {
            Result.Failure(result.error.cleanString())
        }

        val mobileNumberList = (result as Result.Success).data

        val mobileNumberEntityList = MobileNumberEntityMapper.map(mobileNumberList)

        mobileNumberDao.insertMobileNumbers(mobileNumberEntityList)

        val mobileNumberEntityList2 = mobileNumberDao.getAllMobileNumbers()

        val mobileNumberList2 = com.raza.householdrecharge
            .data.remote.mapper
            .MobileNumberDtoMapper.map(mobileNumberEntityList2)

        val result2 = Result.Success(mobileNumberList2)

        return result2
    }

    suspend fun syncMobileNumbers() {
        //Get data from server
        val result = mobileNumberRemoteDataSource.getAllMobileNumbers()

        //Covert DTOs to Room entities
        if(result is Result.Failure) {
            return
        }

        val mobileNumberDtoList = (result as Result.Success).data

        val mobileNumberEntityList = mobileNumberDtoList.map { eachMobileNumberDto ->
            MobileNumberEntityMapper.map(eachMobileNumberDto)
        }

        //Save server data into Room
        mobileNumberDao.insertMobileNumbers(mobileNumberEntityList)
    }
}