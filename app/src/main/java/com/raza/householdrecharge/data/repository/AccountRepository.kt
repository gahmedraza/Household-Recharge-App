package com.raza.householdrecharge.data.repository

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.datasource.AccountRemoteDataSource
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.domain.error.AccountError
import javax.inject.Inject

//TODO Add Dao entries
//TODO the viewmodelscope.launcher should be background
//TODO on an explicit basis as the former does not guarantee
//TODO the execution on a background thread
class AccountRepository @Inject constructor(
    private val accountRemoteDataSource: AccountRemoteDataSource
) {

    /**
     * Transit Method
     * No additional code
     */
    suspend fun createAccount(
        collectionId: String,
        accountDto: AccountDto

    ): Result<Unit, String> {

        return accountRemoteDataSource.createAccount(
            collectionId,
            accountDto
        )
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun updateAccount(
        collectionId: String,
        householdId: String

    ): Result<Unit, AccountError> {

        return accountRemoteDataSource.updateAccount(
            collectionId,
            householdId
        )
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun fetchAccountByAccountId(
        accountId: String

    ): Result<AccountDto, AccountError> {

        return accountRemoteDataSource.fetchAccountByAccountId(
            accountId
        )
    }
}