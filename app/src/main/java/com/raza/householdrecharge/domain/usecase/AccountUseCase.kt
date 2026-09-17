package com.raza.householdrecharge.domain.usecase

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.data.repository.AccountRepository
import com.raza.householdrecharge.domain.error.AccountError
import javax.inject.Inject

class AccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    /**
     * Transit Method
     * No additional code
     */
    suspend fun createAccount(
        collectionId: String,
        accountDto: AccountDto

    ): Result<Unit, String> {

        return accountRepository.createAccount(
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

        return accountRepository.updateAccount(
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

        return accountRepository.fetchAccountByAccountId(
            accountId
        )
    }
}