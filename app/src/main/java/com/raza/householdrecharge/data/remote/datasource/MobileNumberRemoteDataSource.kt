package com.raza.householdrecharge.data.remote.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.HouseholdCollection
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MobileNumberRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun addMobileNumber(
        mobileNumberDto: MobileNumberDto,
    ): Result<MobileNumberDto, String> {

        var result: Result<MobileNumberDto, String>

        try {

            val documentReference = firestore
                .collection(HouseholdCollection.MobileNumbers.description)
                .document()

            val mobileNumberId = documentReference.id

            mobileNumberDto.id = mobileNumberId

            documentReference
                .set(mobileNumberDto)
                .await()

            result = Result.Success(mobileNumberDto)

            /*val documentReference = firestore

                .collection(HouseholdCollection.MobileNumbers.description)
                .add(mobileNumberDto)

                .await()

            val mobileNumberId = documentReference.id

            val newMobileNumberDto = mobileNumberDto.copy(
                id = mobileNumberId
            )

            result = Result.Success(newMobileNumberDto)*/

        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }

    suspend fun getAllMobileNumbers(
    ): Result<List<MobileNumberDto>, String> {

        var result: Result<List<MobileNumberDto>, String>

        try {

            val documentSnapshot = firestore

                .collection(HouseholdCollection.MobileNumbers.description)
                .get()

                .await()

            val mobileNumberList = documentSnapshot.documents.mapNotNull { document ->
                val data = document.data
                val id = document.id
                Log.d("TAG", "data: $data")
                Log.d("TAG","id: $id")

                val mobileNumberDto = document.toObject(MobileNumberDto::class.java)
                mobileNumberDto?.id = document.id
                mobileNumberDto
            }

            result = Result.Success(mobileNumberList)

        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }

    suspend fun updateMobileNumber(
        mobileNumberId: String,
        rechargeId: String
    ): Result<Boolean, String> {

        try {

            firestore
                .collection(HouseholdCollection.MobileNumbers.description)
                .document(mobileNumberId)
                .update("lastRechargeId", rechargeId)
                .await()

            return Result.Success(true)

        } catch(e: Exception) {

            return Result.Failure(e.message.cleanString())
        }
    }
}