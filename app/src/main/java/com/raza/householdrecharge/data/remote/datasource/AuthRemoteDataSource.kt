package com.raza.householdrecharge.data.remote.datasource

import com.google.firebase.auth.FirebaseAuth
import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.data.remote.dto.UserDto
import com.raza.householdrecharge.domain.error.response.AuthResponseError
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    //todo convert to app dto
    fun getUser(
    ): Result<UserDto, AuthResponseError> {

        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser == null) {

            Logger.log("user is not logged in")//todo logging framework
            return Result.Failure(AuthResponseError.UserNotLoggedIn)
        } else {

            val userDto = UserDto()
            userDto.userId = firebaseUser.uid
            userDto.email = firebaseUser.email.cleanString()
            userDto.photoUrl = firebaseUser.photoUrl.toString()

            return Result.Success(userDto)
        }
    }

    suspend fun register(
        authDto: AuthDto
    ): Result<String, AuthResponseError> {

        try {
            val documentReference = firebaseAuth
                .createUserWithEmailAndPassword(authDto.mobileNumber,authDto.password)
                .await()

            val userId = documentReference.user?.uid.cleanString()

            if (userId.isEmpty()) {
                return Result.Failure(AuthResponseError.UserNotCreated)
                //user id was not created during register
            }

            return Result.Success(userId)
        } catch (e: Exception) {

            val error = e.message.cleanString()
            Logger.log(error)

            return Result.Failure(AuthResponseError.Unknown(error))
        }
    }

    suspend fun login(
        authDto: AuthDto
    ): Result<String, String> {

        try {
            val documentReference = firebaseAuth
                .signInWithEmailAndPassword(authDto.mobileNumber,authDto.password)
                .await()

            val userId = documentReference?.user?.uid.cleanString()

            return Result.Success(userId)
        } catch (e: Exception) {

            val error = e.message.cleanString()
            Logger.log(error)

            return Result.Failure(error)
        }
    }
}