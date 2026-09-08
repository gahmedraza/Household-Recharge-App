package com.raza.householdrecharge.domain.usecase

import com.raza.householdrecharge.core.logging.log
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.data.remote.dto.OnboardingDto
import com.raza.householdrecharge.data.repository.AccountRepository
import com.raza.householdrecharge.data.repository.AuthRepository
import com.raza.householdrecharge.util.cleanString
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.repository.account.AccountError
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository
) {

    suspend fun registerAndCreateAccount(
        authDto: AuthDto
    ): Result<OnboardingDto, AccountError> {

        var result: Result<OnboardingDto, AccountError>

        try {

            val onBoardingDto: OnboardingDto

            var accountDto: AccountDto? = null

            val signupResult = authRepository.register(authDto = authDto)

            when (signupResult) {
                is Result.Success -> {

                    accountDto = AccountDto(
                        accountName = authDto.accountName,
                        accountId = signupResult.data.cleanString()
                    )

                    val addAccountResult = accountRepository

                        .createAccount(
                            collectionId = signupResult.data.cleanString(),
                            accountDto = accountDto
                        )

                    if (addAccountResult is Result.Success<Unit>) {

                        onBoardingDto =
                            OnboardingDto(
                                authId = signupResult.data.cleanString(),
                                accountId = signupResult.data.cleanString()
                            )

                        result = Result.Success(onBoardingDto)

                    } else {

                        log("account id was not generated in accounts collection")
                        result = Result.Failure(AccountError.AccountIdNotGenerated)

                    }
                }

                is Result.Failure -> {

                    log(signupResult.error.cleanString())
                    result = Result.Failure(AccountError.Unknown)
                }
            }

        } catch (e: Exception) {

            log(e.message.cleanString())
            result = Result.Failure(AccountError.Unknown)
        }

        return result
    }

    suspend fun loginAndRetrieveAccount(
        authDto: AuthDto
    ): Result<AccountDto, String> {
        //login to firebase
        val result1 = authRepository.login(authDto)

        if(result1 is Result.Failure) {
            return Result.Failure("error")
        }

        val authenticationId = (result1 as Result.Success).data

        //retrieve accountdto and return to caller
        val result2 = accountRepository.fetchAccountByAccountId(
            accountId = authenticationId
        )

        if(result2 is Result.Failure) {
            return Result.Failure("error")
        }

        val accountDto = (result2 as Result.Success).data

        return Result.Success(accountDto)
    }
}