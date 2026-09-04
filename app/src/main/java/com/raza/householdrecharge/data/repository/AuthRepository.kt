package com.raza.householdrecharge.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await

class AuthRepository() {

    suspend fun register(
        authDto: AuthDto
    ): Result<String, String> {

        return try {
            val result = FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword(
                    authDto.mobileNumber,
                    authDto.password
                )
                .await()

            val userId = result.user?.uid.cleanString()

            if (userId.isEmpty()) {
                Result.Failure<String>("user id was not created during signup")
            }

            Result.Success<String>(userId)

        } catch (e: Exception) {
            Result.Failure<String>(e.message.cleanString())
        }
    }

    fun login(
        authDto: AuthDto,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(authDto.mobileNumber, authDto.password)
            .addOnSuccessListener { documentReference ->

                val userId = documentReference?.user?.uid.cleanString()
                onSuccess(userId)
            }
            .addOnFailureListener {

                onFailure(it.message.cleanString())
            }
    }
}