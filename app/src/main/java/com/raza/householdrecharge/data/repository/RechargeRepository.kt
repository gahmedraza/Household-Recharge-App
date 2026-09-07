package com.raza.householdrecharge.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.domain.model.RechargeHistory
import com.raza.householdrecharge.util.cleanString
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.RechargeDto
import kotlinx.coroutines.tasks.await

class RechargeRepository(
    private val firestore: FirebaseFirestore
) {

    suspend fun addRecharge(
        rechargeDto: RechargeDto,
        appUserDto: AppUserDto
    ): Result<String, String> {

        var result: Result<String, String>

        try {

            val documentReference = firestore

                .collection("recharges")
                .add(rechargeDto)

                .await()

            val rechargeHistoryId = documentReference.id

            result = Result.Success(rechargeHistoryId)


        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }

    suspend fun fetchRechargeHistoryList(
        appUserDto: AppUserDto
    ): Result<List<RechargeHistory>, String> {

        var result: Result<List<RechargeHistory>, String>

        try {

            val documentSnapshot = firestore

                .collection("users")
                .document(appUserDto.authId)

                .collection("households")
                .document(appUserDto.householdId)

                .collection("members")
                .document(appUserDto.memberId)

                .collection("mobileNumbers")
                .document(appUserDto.mobileNumber)

                .collection("recharges")
                .get()
                .await()

            val rechargeHistoryList = documentSnapshot.documents.mapNotNull { document ->
                document.toObject(RechargeHistory::class.java)
            }

            result = Result.Success(rechargeHistoryList)

        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }
}