package com.raza.householdrecharge.data.repository

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.datasource.HouseholdRemoteDataSource
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.data.remote.dto.HouseholdDto
import com.raza.householdrecharge.domain.error.HouseholdError
import javax.inject.Inject

class HouseholdRepository @Inject constructor(
    private val householdRemoteDataSource: HouseholdRemoteDataSource
) {

    /**
     * Transit Method
     * No additional code
     */
    suspend fun addHousehold(
        appUserDto: AppUserDto,
        householdDto: HouseholdDto
    ): Result<String, String> {

        return householdRemoteDataSource.addHousehold(
            appUserDto,
            householdDto
        )
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun getHouseholdByHouseholdId(
        householdId: String
    ): Result<HouseholdDto, HouseholdError>{

        return householdRemoteDataSource.getHouseholdByHouseholdId(
            householdId
        )
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

        return householdRemoteDataSource.joinHousehold(
            userId,
            householdId,
            invitationCode
        )
    }
}