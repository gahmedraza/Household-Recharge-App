package com.raza.householdrecharge.data.remote.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.CollectionField
import com.raza.householdrecharge.data.remote.HouseholdCollection
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.data.remote.dto.HouseholdDto
import com.raza.householdrecharge.domain.error.response.HouseholdResponseError
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HouseholdRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun addHousehold(
        appUserDto: AppUserDto,
        householdDto: HouseholdDto
    ): Result<String, HouseholdResponseError> {

        try {
            householdDto.accountId = appUserDto.authId

            val documentReference = firestore

                .collection(HouseholdCollection.Households.description)
                .document()

            householdDto.householdId = documentReference.id


            documentReference.set(householdDto)
                .await()

            val householdId = documentReference.id.cleanString()

            if (householdId.isEmpty()) {
                return Result.Failure(HouseholdResponseError.HouseholdIdNotGenerated)
            }

            return Result.Success(householdId)
        } catch (e: Exception) {
            val error = e.message.cleanString()
            return Result.Failure(HouseholdResponseError.Unknown(error))
        }

    }

    suspend fun getHouseholdByHouseholdId(
        householdId: String
    ): Result<HouseholdDto, HouseholdResponseError>{

        val snapshot = firestore
            .collection(HouseholdCollection.Households.description)
            .document(householdId)
            .get()
            .await()

        if(!snapshot.exists()) {
            return Result.Failure(HouseholdResponseError.NoHouseholdFound)
        }

        val householdDto = snapshot.toObject(HouseholdDto::class.java)

        if(householdDto == null) {
            return Result.Failure(HouseholdResponseError.HouseholdDataMappingError)

        } else {
            return Result.Success(householdDto)
        }
    }
}