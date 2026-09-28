package com.raza.householdrecharge.data.remote.datasource

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.CollectionField
import com.raza.householdrecharge.data.remote.HouseholdCollection
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.domain.error.response.AccountResponseError
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await

class AccountRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun createAccount(
        collectionId: String,
        accountDto: AccountDto

    ): Result<Unit, AccountResponseError> {

        try {

            firestore

                .collection(HouseholdCollection.Accounts.description)
                .document(collectionId)
                .set(accountDto)

                .await()

            return Result.Success(Unit)

        } catch (e: Exception) {
            val error = e.message.cleanString()
            //Logger.log(error) //todo framework

            return Result.Failure(AccountResponseError.Unknown(error))
        }
    }

    suspend fun updateAccount(
        collectionId: String,
        householdId: String

    ): Result<Unit, AccountResponseError> {

        try {

            firestore

                .collection(HouseholdCollection.Accounts.description)
                .document(collectionId)
                .update(CollectionField.HouseholdID.description, householdId)

                .await()

            return Result.Success(Unit)

        } catch (e: Exception) {
            val error = e.message.cleanString()
            //Logger.log(error)//todo

            return Result.Failure(AccountResponseError.Unknown(error))
        }
    }

    suspend fun fetchAccountByAccountId(
        accountId: String

    ): Result<AccountDto, AccountResponseError> {

        try {

            val document =
                firestore

                    .collection(HouseholdCollection.Accounts.description)
                    .document(accountId)
                    .get()

                    .await()

            if(document == null) {
                return Result.Failure(AccountResponseError.NoRecordFound)
            }

            val accountDto = document.toObject(AccountDto::class.java)

            if(accountDto == null) {
                return Result.Failure(AccountResponseError.DataParsingError)

            } else {
                return Result.Success(accountDto)

            }

        } catch (e: Exception) {
            val error = e.message.cleanString()
            //Logger.log(error)

            return Result.Failure(AccountResponseError.Unknown(error))
        }
    }
}