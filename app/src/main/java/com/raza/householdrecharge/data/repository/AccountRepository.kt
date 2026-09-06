package com.raza.householdrecharge.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.common.log
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.HouseholdCollection
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.data.repository.account.AccountError
import com.raza.householdrecharge.data.repository.account.HouseholdID
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

        var result : Result<Unit, String>

        try {

            firestore

                .collection(HouseholdCollection.Accounts.description)
                .document(collectionId)
                .set(accountDto)

                .await()

            result = Result.Success(Unit)

        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }

    /**
     * update household ID
     * in the account record
     */
    suspend fun updateAccount(
        collectionId: String,
        householdId: String

    ): Result<Unit, AccountError> {

        var result : Result<Unit, AccountError>

        try {

            firestore

                .collection(HouseholdCollection.Accounts.description)
                .document(collectionId)
                .update(HouseholdID, householdId)

                .await()

            result = Result.Success(Unit)

        } catch (e: Exception) {

            log(e.message)
            result = Result.Failure(AccountError.Unknown)
        }

        return result
    }

    /**
     * Fetch the account record
     * with the record ID
     */
    suspend fun fetchAccountByAccountId(
        accountId: String

    ): Result<AccountDto, AccountError> {

        var result : Result<AccountDto, AccountError>

        try {

            val document =

                firestore

                    .collection(HouseholdCollection.Accounts.description)
                    .document(accountId)
                    .get()

                    .await()

            val accountDto = document.toObject(AccountDto::class.java)

            if(accountDto == null) {
                result = Result.Failure(AccountError.AccountEmpty)

            } else {
                result = Result.Success(accountDto)

            }

        } catch (e: Exception) {
            log(e.message)
            result = Result.Failure(AccountError.Unknown)

        }

        return result
    }
}