package com.raza.householdrecharge.data.repository

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.local.dao.UserDao
import com.raza.householdrecharge.data.local.entity.UserEntity
import com.raza.householdrecharge.data.remote.datasource.AuthRemoteDataSource
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.data.remote.dto.UserDto
import com.raza.householdrecharge.domain.error.AuthError
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val userDao: UserDao
) {

    /**
     * Transit Method
     * No additional code
     */
    fun getUser(

    ): Result<UserDto, AuthError>
    //FirebaseUser?
    {

        return authRemoteDataSource.getUser()
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun register(
        authDto: AuthDto
    ): Result<String, String> {

        val result51 = authRemoteDataSource.register(
            authDto
        )

        if(result51 is Result.Failure) {
            return result51
        }

        val userId = (result51 as Result.Success).data

        //todo remove
        val userEntity = UserEntity(
            userId = userId,
            email = authDto.mobileNumber,
            password = authDto.password
        )

        userDao.upsertUser(userEntity)

        return result51
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun login(
        authDto: AuthDto
    ): Result<String, String> {

        val result51 = authRemoteDataSource.login(
            authDto
        )

        if(result51 is Result.Failure) {
            return result51
        }

        val userId = (result51 as Result.Success).data

        val userEntity = UserEntity(
            userId = userId,
            email = authDto.mobileNumber,
            password = authDto.password
        )

        userDao.upsertUser(userEntity)

        return result51
    }
}