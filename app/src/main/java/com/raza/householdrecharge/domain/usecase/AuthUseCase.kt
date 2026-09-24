package com.raza.householdrecharge.domain.usecase

import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.data.remote.dto.OnboardingDto
import com.raza.householdrecharge.data.repository.AccountRepository
import com.raza.householdrecharge.data.repository.AuthRepository
import com.raza.householdrecharge.util.cleanString
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.UserDto
import com.raza.householdrecharge.domain.error.AccountError
import com.raza.householdrecharge.domain.error.AuthError
import com.raza.householdrecharge.domain.model.Account
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

            var accountDto: AccountDto?

            val registerResult = authRepository.register(authDto = authDto)

            when (registerResult) {
                is Result.Success -> {

                    accountDto = AccountDto(
                        accountName = authDto.accountName,
                        accountId = registerResult.data.cleanString()
                    )

                    val addAccountResult = accountRepository

                        .createAccount(
                            collectionId = registerResult.data.cleanString(),
                            accountDto = accountDto
                        )

                    if (addAccountResult is Result.Success<Unit>) {

                        onBoardingDto =
                            OnboardingDto(
                                authId = registerResult.data.cleanString(),
                                accountId = registerResult.data.cleanString()
                            )

                        result = Result.Success(onBoardingDto)

                    } else {

                        val error = "account id was not generated in accounts collection"
                        Logger.log(error)
                        result = Result.Failure(AccountError.AccountIdNotGenerated(error))

                    }
                }

                is Result.Failure -> {
                    val error = registerResult.error.cleanString()
                    Logger.log(error)
                    result = Result.Failure(AccountError.Unknown(error))
                }
            }

        } catch (e: Exception) {
            val error = e.message.cleanString()
            Logger.log(error)
            result = Result.Failure(AccountError.Unknown(error))
        }

        return result
    }

    suspend fun loginAndRetrieveAccount(
        authDto: AuthDto
    ): Result<Account, String> {
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

    /**
     * Transit Method
     * No additional code
     */
    suspend fun login(
        authDto: AuthDto
    ): Result<String, String> {

        return authRepository.login(
            authDto
        )
    }

    /**
     * Transit Method
     * No additional code
     */
    fun getUser(

    ): Result<UserDto, AuthError>
    //FirebaseUser?
    {
        return authRepository.getUser()
    }
}