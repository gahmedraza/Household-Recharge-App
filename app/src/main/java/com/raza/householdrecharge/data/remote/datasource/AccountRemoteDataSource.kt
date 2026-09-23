package com.raza.householdrecharge.data.remote.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.core.logging.Logger
import javax.inject.Inject
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.CollectionField
import com.raza.householdrecharge.data.remote.HouseholdCollection
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.domain.error.AccountError
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await

class AccountRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    /**
     * Create an account record
     * with provided collection ID
     */
    suspend fun createAccount(
        collectionId: String,
        accountDto: AccountDto

    ): Result<Unit, AccountError> {

        try {

            firestore

                .collection(HouseholdCollection.Accounts.description)
                .document(collectionId)
                .set(accountDto)

                .await()

            return Result.Success(Unit)

        } catch (e: Exception) {
            Logger.log(e.message.cleanString())

            return Result.Failure(AccountError.Unknown)
        }
    }

    /**
     * update household ID
     * in the account record
     */
    suspend fun updateAccount(
        collectionId: String,
        householdId: String

    ): Result<Unit, AccountError> {

        try {

            firestore

                .collection(HouseholdCollection.Accounts.description)
                .document(collectionId)
                .update(CollectionField.HouseholdID.description, householdId)

                .await()

            return Result.Success(Unit)

        } catch (e: Exception) {

            Logger.log(e.message)
            return Result.Failure(AccountError.Unknown)
        }
    }

    /**
     * Fetch the account record
     * with the record ID
     */
    suspend fun fetchAccountByAccountId(
        accountId: String

    ): Result<AccountDto, AccountError> {

        try {

            val document =

                firestore

                    .collection(HouseholdCollection.Accounts.description)
                    .document(accountId)
                    .get()

                    .await()

            val accountDto = document.toObject(AccountDto::class.java)

            if(accountDto == null) {
                return Result.Failure(AccountError.AccountEmpty)

            } else {
                return Result.Success(accountDto)

            }

        } catch (e: Exception) {

            Logger.log(e.message)
            return Result.Failure(AccountError.Unknown)
        }
    }
}