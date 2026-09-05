package com.raza.householdrecharge.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.common.log
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.HouseholdCollection
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.data.repository.account.AccountError
import com.raza.householdrecharge.data.repository.account.HouseHoldID
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await

class AccountRepository(
    private val firestore: FirebaseFirestore
) {

    /**
     * Create an account record
     * with provided collection ID
     */
    suspend fun createAccount(
        collectionId: String,
        accountDto: AccountDto

    ): Result<Unit, String> {

        try {

            firestore

                .collection(HouseholdCollection.Accounts.description)
                .document(collectionId)
                .set(accountDto)

                .await()

            return Result.Success(Unit)

        } catch (e: Exception) {

            return Result.Failure(e.message.cleanString())
        }
    }

    /**
     * update household ID
     * in the account record
     */
    suspend fun updateAccount(
        collectionId: String,
        householdId: String

    ): Result<Unit, String> {
        return try {

            firestore

                .collection(HouseholdCollection.Accounts.description)
                .document(collectionId)
                .update(HouseHoldID, householdId)

                .await()

            Result.Success(Unit)

        } catch (e: Exception) {

            Result.Failure(e.message.cleanString())
        }
    }

    /**
     * Fetch the account record
     * with the record ID
     */
    suspend fun fetchAccount(
        collectionId: String

    ): Result<AccountDto, AccountError> {
        try {

            val document =

                firestore

                    .collection(HouseholdCollection.Accounts.description)
                    .document(collectionId)
                    .get()

                    .await()

            val accountDto = document.toObject(AccountDto::class.java)

            return if(accountDto == null) {
                Result.Failure(AccountError.AccountEmpty)

            } else {
                Result.Success(accountDto)

            }

        } catch (e: Exception) {
            log(e.message)
            return Result.Failure(AccountError.Unknown)

        }
    }
}