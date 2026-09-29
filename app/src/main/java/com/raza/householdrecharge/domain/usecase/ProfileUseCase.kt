package com.raza.householdrecharge.domain.usecase

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.repository.AccountRepository
import com.raza.householdrecharge.domain.error.usecase.ProfileUseCaseError
import com.raza.householdrecharge.domain.model.Account
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    /**
     * Transit Method
     * No additional code
     */
    suspend fun fetchProfileByAccountId(
        accountId: String

    ): Result<Account, ProfileUseCaseError> {

        return accountRepository.fetchAccountByAccountId(
            accountId
        )
    }
}