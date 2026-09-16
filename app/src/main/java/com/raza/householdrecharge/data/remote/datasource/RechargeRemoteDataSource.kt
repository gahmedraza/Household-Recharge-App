package com.raza.householdrecharge.data.remote.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.RechargeDto
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RechargeRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun addRecharge(
        rechargeDto: RechargeDto
    ): Result<RechargeDto, String> {

        var result: Result<RechargeDto, String>

        try {

            val documentReference = firestore

                .collection("recharges")
                .document()

            val rechargeId = documentReference.id

            val rechargeDto = rechargeDto.copy(
                id = rechargeId
            )

            documentReference
                .set(rechargeDto)
                .await()

            result = Result.Success(rechargeDto)

            /*val documentReference = firestore

                .collection("recharges")
                .add(rechargeDto)

                .await()

            val rechargeId = documentReference.id

            val newRechargeDto = rechargeDto.copy(
                id = rechargeId
            )

            result = Result.Success(newRechargeDto)*/


        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }

    suspend fun getAllRecharges(
    ): Result<List<RechargeDto>, String> {

        var result: Result<List<RechargeDto>, String>

        try {

            val documentSnapshot = firestore

                .collection("recharges")
                .get()
                .await()

            val rechargeList = documentSnapshot.documents.mapNotNull { document ->
                document.toObject(RechargeDto::class.java)
            }

            result = Result.Success(rechargeList)

        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }
}