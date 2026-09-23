package com.raza.householdrecharge.data.repository

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.local.dao.AccountDao
import com.raza.householdrecharge.data.remote.datasource.AccountRemoteDataSource
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.data.remote.mapper.account.AccountEntityMapper
import com.raza.householdrecharge.data.remote.mapper.account.AccountMapper
import com.raza.householdrecharge.domain.error.AccountError
import com.raza.householdrecharge.domain.model.Account
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val accountRemoteDataSource: AccountRemoteDataSource,
    private val accountDao: AccountDao
) {

    suspend fun createAccount(
        collectionId: String,
        accountDto: AccountDto

    ): Result<Unit, AccountError> {

        val result51 = accountRemoteDataSource.createAccount(
            collectionId,
            accountDto
        )

        if(result51 is Result.Failure) {
            return result51
        }

        val accountEntity = AccountEntityMapper.map(accountDto)

        accountDao.upsertAccount(accountEntity)

        return result51
    }

    suspend fun updateAccount(
        collectionId: String,
        householdId: String

    ): Result<Unit, AccountError> {

        val result51 = accountRemoteDataSource.updateAccount(
            collectionId,
            householdId
        )

        if(result51 is Result.Failure) {
            return result51
        }

        accountDao.updateAccount(collectionId, householdId)

        return result51
    }

    suspend fun fetchAccountByAccountId(
        accountId: String

    ): Result<Account, AccountError> {

        val result51 = accountRemoteDataSource.fetchAccountByAccountId(
            accountId
        )

        if(result51 is Result.Failure) {
            return result51
        }

        val account = (result51 as Result.Success).data

        val accountEntity = AccountEntityMapper.map(account)

        accountDao.upsertAccount(accountEntity)

        val accountModel = AccountMapper.map(account)

        return Result.Success(accountModel) //todo remove and add flowstate observable
    }
}