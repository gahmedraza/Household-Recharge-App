package com.raza.householdrecharge.data.repository

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.local.dao.HouseholdDao
import com.raza.householdrecharge.data.remote.datasource.HouseholdRemoteDataSource
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.data.remote.dto.HouseholdDto
import com.raza.householdrecharge.data.remote.mapper.household.HouseholdEntityMapper
import com.raza.householdrecharge.data.remote.mapper.household.HouseholdMapper
import com.raza.householdrecharge.domain.error.HouseholdError
import com.raza.householdrecharge.domain.model.Household
import javax.inject.Inject

class HouseholdRepository @Inject constructor(
    private val householdRemoteDataSource: HouseholdRemoteDataSource,
    private val householdDao: HouseholdDao
) {

    /**
     * Transit Method
     * No additional code
     */
    suspend fun addHousehold(
        appUserDto: AppUserDto,
        householdDto: HouseholdDto
    ): Result<String, String> {

        val result51 = householdRemoteDataSource.addHousehold(
            appUserDto,
            householdDto
        )

        if(result51 is Result.Failure) {
            return result51
        }

        val householdId = (result51 as Result.Success).data

        val householdEntity = HouseholdEntityMapper.map(householdDto)

        //todo remove
        householdEntity.householdId = householdId

        householdDao.upsertHousehold(householdEntity)

        return result51
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun getHouseholdByHouseholdId(
        householdId: String
    ): Result<Household, HouseholdError>{

        val result51 = householdRemoteDataSource.getHouseholdByHouseholdId(
            householdId
        )

        if(result51 is Result.Failure) {
            return result51
        }

        val householdDto = (result51 as Result.Success).data

        val householdEntity = HouseholdEntityMapper.map(householdDto)

        householdEntity.householdId = householdId

        householdDao.upsertHousehold(householdEntity)

        val household = HouseholdMapper.map(householdEntity)

        return Result.Success(household)
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun joinHousehold(
        userId: String,
        householdId: String,
        invitationCode: String
    ): Result<String, String> {

        //todo dao required
        return householdRemoteDataSource.joinHousehold(
            userId,
            householdId,
            invitationCode
        )
    }
}