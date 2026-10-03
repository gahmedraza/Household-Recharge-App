package com.raza.householdrecharge.data.remote.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.CollectionField
import com.raza.householdrecharge.data.remote.HouseholdCollection
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto
import com.raza.householdrecharge.domain.error.response.MobileNumberResponseError
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MobileNumberRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun addMobileNumber(
        mobileNumberDto: MobileNumberDto,
    ): Result<MobileNumberDto, MobileNumberResponseError> {

        try {

            val documentReference = firestore
                .collection(HouseholdCollection.MobileNumbers.description)
                .document()

            val mobileNumberId = documentReference.id

            mobileNumberDto.id = mobileNumberId

            documentReference
                .set(mobileNumberDto)
                .await()

            return Result.Success(mobileNumberDto)
        } catch (e: Exception) {

            return Result.Failure(
                MobileNumberResponseError.Unknown(
                    e.message.cleanString()
                )
            )
        }
    }

    suspend fun getAllMobileNumbers(
        householdId: String
    ): Result<List<MobileNumberDto>, MobileNumberResponseError> {

        try {

            val documentSnapshot = firestore

                .collection(HouseholdCollection.MobileNumbers.description)
                .whereEqualTo(CollectionField.HouseholdID.description, householdId)
                .get()

                .await()

            val mobileNumberList = documentSnapshot.documents.mapNotNull { document ->
                val data = document.data
                val id = document.id
                Logger.log("data: $data")
                Logger.log("id: $id")

                val mobileNumberDto = document.toObject(MobileNumberDto::class.java)
                mobileNumberDto?.id = document.id
                mobileNumberDto
            }

            return Result.Success(
                mobileNumberList
            )

        } catch (e: Exception) {

            return Result.Failure(
                MobileNumberResponseError.Unknown(
                    e.message.cleanString()
                )
            )
        }
    }

    suspend fun updateMobileNumber(
        mobileNumberId: String,
        rechargeId: String
    ): Result<Boolean, MobileNumberResponseError> {

        try {

            firestore
                .collection(HouseholdCollection.MobileNumbers.description)
                .document(mobileNumberId)
                .update(CollectionField.LastRechargeID.description, rechargeId)
                .await()

            return Result.Success(
                true
            )
        } catch(e: Exception) {

            return Result.Failure(
                MobileNumberResponseError.Unknown(
                    e.message.cleanString()
                )
            )
        }
    }
}