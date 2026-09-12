package com.raza.householdrecharge.data.repository

import com.raza.householdrecharge.data.local.dao.MobileNumberDao
import com.raza.householdrecharge.data.remote.datasource.MobileNumberRemoteDataSource
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.mapper.MobileNumberEntity
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

    suspend fun getAllMobileNumbers(
    ): Result<List<MobileNumberDto>, String> {

        return mobileNumberRemoteDataSource.getAllMobileNumbers()
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
            MobileNumberEntity.map(eachMobileNumberDto)
        }

        //Save server data into Room
        mobileNumberDao.insertMobileNumbers(mobileNumberEntityList)
    }
}