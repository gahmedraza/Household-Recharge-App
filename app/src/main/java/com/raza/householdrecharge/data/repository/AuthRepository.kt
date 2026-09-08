package com.raza.householdrecharge.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.raza.householdrecharge.core.logging.log
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.data.repository.account.AuthError
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val firebaseAuth: FirebaseAuth
) {

    //todo convert to app dto
    fun getUser(

    ): Result<UserDto, AuthError>
    //FirebaseUser?
    {
        var result: Result<UserDto, AuthError>

        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser == null) {

            log("user is not logged in")
            result = Result.Failure(AuthError.UserNotLoggedIn)

        } else {

            val userDto = UserDto()
            userDto.userId = firebaseUser.uid
            userDto.email = firebaseUser.email.cleanString()
            userDto.photoUrl = firebaseUser.photoUrl.toString()

            result = Result.Success(userDto)
        }

        return result
    }

    suspend fun register(
        authDto: AuthDto
    ): Result<String, String> {

        var result: Result<String, String>

        try {
            val documentReference = firebaseAuth
                .createUserWithEmailAndPassword(
                    authDto.mobileNumber,
                    authDto.password
                )
                .await()

            val userId = documentReference.user?.uid.cleanString()

            if (userId.isEmpty()) {
                result = Result.Failure<String>("user id was not created during signup")
            }

            result = Result.Success<String>(userId)

        } catch (e: Exception) {
            result = Result.Failure<String>(e.message.cleanString())
        }

        return result
    }

    suspend fun login(
        authDto: AuthDto
    ): Result<String, String> {

        var result: Result<String, String>

        try {
            val documentReference = firebaseAuth
                .signInWithEmailAndPassword(authDto.mobileNumber, authDto.password)
                .await()

            val userId = documentReference?.user?.uid.cleanString()
            result = Result.Success(userId)


        } catch(e: Exception) {
            result = Result.Failure(e.message.cleanString())
        }

        return result
    }
}

data class UserDto(
    var userId: String = "",
    var email: String = "",
    var photoUrl: String = ""
)