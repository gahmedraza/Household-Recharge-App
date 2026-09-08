package com.raza.householdrecharge.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.domain.model.Recharge
import com.raza.householdrecharge.util.cleanString
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.RechargeDto
import kotlinx.coroutines.tasks.await

class RechargeRepository(
    private val firestore: FirebaseFirestore
) {

    suspend fun addRecharge(
        rechargeDto: RechargeDto
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

    suspend fun getAllRecharges(
    ): Result<List<Recharge>, String> {

        var result: Result<List<Recharge>, String>

        try {

            val documentSnapshot = firestore

                .collection("recharges")
                .get()
                .await()

            val rechargeList = documentSnapshot.documents.mapNotNull { document ->
                document.toObject(Recharge::class.java)
            }

            result = Result.Success(rechargeList)

        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }
}