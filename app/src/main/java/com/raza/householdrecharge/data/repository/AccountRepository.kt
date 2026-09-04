package com.raza.householdrecharge.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.HouseholdCollection
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await

class AccountRepository() {

    suspend fun addAccount(
        accountDto: AccountDto?
    ): Result<String, String> {
        return try {

            if (accountDto == null) {
                return Result.Failure("account collection cannot be updated with empty account dto")
            }

            val documentReference = FirebaseFirestore
                .getInstance()

                .collection(HouseholdCollection.Accounts.description)
                .document(accountDto.accountId.cleanString())
                .set(accountDto)

                .await()

            val accountId = accountDto.accountId.cleanString()

            if (accountId.isEmpty()) {
                Result.Failure("account id was not generated in accounts collection")
            }

            Result.Success(accountId)
        } catch (e: Exception) {

            Result.Failure(e.message.cleanString())
        }
    }

    suspend fun updateAccount(
        accountId: String, householdId: String
    ): Result<String, String> {
        return try {

            FirebaseFirestore
                .getInstance()

                .collection(HouseholdCollection.Accounts.description)
                .document(accountId)

                .update("householdId", householdId)

                .await()

            Result.Success(accountId)
        } catch (e: Exception) {

            Result.Failure(e.message.cleanString())
        }
    }

    suspend fun fetchAccount(
        accountDto: AccountDto?
    ): Result<AccountDto?, String> {
        return try {

            if (accountDto == null) {
                return Result.Failure("account collection cannot be fetched with empty account dto")
            }

            val accountId = accountDto.accountId.cleanString()

            if (accountId.isEmpty()) {
                Result.Failure("account collection cannot be fetched with empty account id")
            }

            val document = FirebaseFirestore
                .getInstance()

                .collection(HouseholdCollection.Accounts.description)
                .document(accountDto.accountId.cleanString())
                .get()

                .await()



            val accountDto = document.toObject(AccountDto::class.java)

            Result.Success(accountDto)
        } catch (e: Exception) {

            Result.Failure(e.message.cleanString())
        }
    }
}