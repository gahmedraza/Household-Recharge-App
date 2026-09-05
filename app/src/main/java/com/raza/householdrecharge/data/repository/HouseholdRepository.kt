package com.raza.householdrecharge.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.common.HouseholdDto
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await

class HouseholdRepository(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun addHousehold(
        appUserDto: AppUserDto,
        householdDto: HouseholdDto
    ): Result<String, String> {

        var result: Result<String, String>

        try {
            householdDto.authId = appUserDto.authId

            val documentReference = firestore

                .collection("households")
                .add(householdDto)
                .await()

            val householdId = documentReference.id.cleanString()

            if (householdId.isEmpty()) {
                result = Result.Failure("household id was not generated in households collection")
            }

            result = Result.Success(householdId)
        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }

    suspend fun fetchHousehold(
    ): Result<String, String> {

        var result: Result<String, String>

        val firebaseUser = firebaseAuth
            .currentUser

        val firebaseUserIdNotFound = firebaseUser?.uid?.isEmpty() ?: false

        if (firebaseUserIdNotFound) {
            result = Result.Failure("Firebase user id does not exist")
        }

        try {
            val documentSnapshot = firestore

                .collection("users")
                .document(firebaseUser?.uid.cleanString())

                .get()
                .await()

            if (!documentSnapshot.exists()) {
                result = Result.Failure("User data not found")
            }

            val householdId = documentSnapshot.getString("householdId")
            result = Result.Success(householdId.cleanString())

        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }

    suspend fun joinHousehold(
        userId: String,
        householdId: String,
        invitationCode: String
    ): Result<String, String> {

        var result: Result<String, String>

        try {

            firestore.runTransaction { transaction ->

                val invitationReference = firestore
                    .collection("invitations")
                    .document(invitationCode.uppercase())

                val accountReference = firestore
                    .collection("accounts")
                    .document(userId)

                val invitationSnapshot = transaction.get(invitationReference)

                if(!invitationSnapshot.exists()) {
                    result = Result.Failure("invitation does not exist")
                }

                val status = invitationSnapshot.getString("status")

                if(status != "pending") {
                    result = Result.Failure("invitation has already been used")
                }

                val invitationHouseholdId = invitationSnapshot.getString("householdId")

                if(invitationHouseholdId != householdId) {
                    result = Result.Failure("invalid invitation")
                }

                val expiresAt = invitationSnapshot.getString("expiresAt")?.toLong()

                if(expiresAt != null && expiresAt < System.currentTimeMillis()) {
                    result = Result.Failure("invitation has expired")
                }

                transaction.update(
                    accountReference,
                    "householdId",
                    householdId
                )

                transaction.update(
                    invitationReference,
                    mapOf(
                        "status" to "used",
                        "usedBy" to userId,
                        "usedAt" to System.currentTimeMillis()
                    )
                )
            }.await()

            result = Result.Success("success")

        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }
}