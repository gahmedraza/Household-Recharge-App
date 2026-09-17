package com.raza.householdrecharge.data.repository

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.datasource.AuthRemoteDataSource
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.data.remote.dto.UserDto
import com.raza.householdrecharge.data.repository.account.AuthError
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
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

        return authRemoteDataSource.register(
            authDto
        )
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun login(
        authDto: AuthDto
    ): Result<String, String> {

        return authRemoteDataSource.login(
            authDto
        )
    }
}