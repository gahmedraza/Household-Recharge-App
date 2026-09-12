package com.raza.householdrecharge.data.remote.datasource

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
    ): Result<String, String> {

        var result: Result<String, String>

        try {

            val documentReference = firestore

                .collection(HouseholdCollection.MobileNumbers.description)
                .add(mobileNumberDto)

                .await()

            val mobileNumberId = documentReference.id

            result = Result.Success(mobileNumberId)


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
                document.toObject(MobileNumberDto::class.java)
            }

            result = Result.Success(mobileNumberList)

        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }
}