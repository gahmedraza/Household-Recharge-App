package com.raza.householdrecharge.data.remote.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.CollectionField
import com.raza.householdrecharge.data.remote.HouseholdCollection
import com.raza.householdrecharge.data.remote.dto.RechargeDto
import com.raza.householdrecharge.domain.error.response.RechargeResponseError
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RechargeRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun addRecharge(
        rechargeDto: RechargeDto
    ): Result<RechargeDto, RechargeResponseError> {

        try {

            val documentReference = firestore

                .collection(HouseholdCollection.Recharges.description)
                .document()

            val rechargeId = documentReference.id

            val rechargeDto = rechargeDto.copy(
                id = rechargeId
            )

            documentReference
                .set(rechargeDto)
                .await()

            return Result.Success(
                rechargeDto
            )
        } catch (e: Exception) {

            return Result.Failure(
                RechargeResponseError.Unknown(
                    e.message.cleanString()
                )
            )
        }
    }

    suspend fun getAllRecharges(
        householdId: String
    ): Result<List<RechargeDto>, RechargeResponseError> {

        try {

            val documentSnapshot = firestore

                .collection(HouseholdCollection.Recharges.description)
                .whereEqualTo(CollectionField.HouseholdID.description, householdId)
                .get()
                .await()

            val rechargeList = documentSnapshot.documents.mapNotNull { document ->
                document.toObject(RechargeDto::class.java)
            }

            return Result.Success(
                rechargeList
            )
        } catch (e: Exception) {

            return Result.Failure(
                RechargeResponseError.Unknown(
                    e.message.cleanString()
                )
            )
        }
    }
}