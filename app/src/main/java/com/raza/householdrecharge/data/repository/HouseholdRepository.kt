package com.raza.householdrecharge.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.common.HouseholdDto
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await

class HouseholdRepository() {

    suspend fun addHousehold(
        appUserDto: AppUserDto,
        householdDto: HouseholdDto
    ): Result<String, String> {

        return try {
            householdDto.authId = appUserDto.authId

            val documentReference = FirebaseFirestore
                .getInstance()

                .collection("households")
                .add(householdDto)
                .await()

            val householdId = documentReference.id.cleanString()

            if (householdId.isEmpty()) {
                Result.Failure("household id was not generated in households collection")
            }

            Result.Success(householdId)
        } catch (e: Exception) {

            Result.Failure(e.message.cleanString())
        }
    }

    fun fetchHousehold(
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val firebaseUser = FirebaseAuth
            .getInstance()
            .currentUser

        val firebaseUserIdNotFound = firebaseUser?.uid?.isEmpty() ?: false

        if (firebaseUserIdNotFound) {
            onFailure("Firebase user id does not exist")
            return
        }


        FirebaseFirestore
            .getInstance()

            .collection("users")
            .document(firebaseUser?.uid.cleanString())

            .get()

            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    onFailure("User data not found")
                }

                val householdId = document.getString("householdId")
                onSuccess(householdId.cleanString())
            }

            .addOnFailureListener {

                onFailure(it.message.cleanString())
            }
    }

    suspend fun joinHousehold(
        userId: String,
        householdId: String,
        invitationCode: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val firestore = FirebaseFirestore.getInstance()

        firestore.runTransaction { transaction ->

            val invitationReference = firestore
                .collection("invitations")
                .document(invitationCode.uppercase())

            val accountReference = firestore
                .collection("accounts")
                .document(userId)

            val invitationSnapshot = transaction.get(invitationReference)

            if(!invitationSnapshot.exists()) {
                onFailure("invitation does not exist")
            }

            val status = invitationSnapshot.getString("status")

            if(status != "pending") {
                onFailure("invitation has already been used")
            }

            val invitationHouseholdId = invitationSnapshot.getString("householdId")

            if(invitationHouseholdId != householdId) {
                onFailure("invalid invitation")
            }

            val expiresAt = invitationSnapshot.getString("expiresAt")?.toLong()

            if(expiresAt != null && expiresAt < System.currentTimeMillis()) {
                onFailure("invitation has expired")
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

        onSuccess("success")
    }
}