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
import com.raza.householdrecharge.data.remote.factory.MobileNumberDtoFactory
import com.raza.householdrecharge.domain.error.response.AccountResponseError
import com.raza.householdrecharge.domain.error.response.AuthResponseError
import com.raza.householdrecharge.domain.error.usecase.AuthUseCaseError
import com.raza.householdrecharge.domain.model.Account
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository
) {

    suspend fun registerAndCreateAccount(
        authDto: AuthDto
    ): Result<OnboardingDto, AuthUseCaseError> {

        var result: Result<OnboardingDto, AccountResponseError>

        try {

            val onBoardingDto: OnboardingDto

            var accountDto: AccountDto?

            val registerResult = authRepository.register(authDto = authDto)

            if (registerResult is Result.Failure) {
                val error = registerResult.error.toString()
                Logger.log(error)
                return Result.Failure(AccountResponseError.Unknown(error))
            }

            //
            val accountId = (registerResult as Result.Success).data.cleanString()

            accountDto = AccountDto(
                accountName = authDto.accountName,
                accountId = accountId,
                primaryMobileNumber = authDto.mobileNumber
            )

            val addAccountResult = accountRepository

                .createAccount(
                    collectionId = accountId,
                    accountDto = accountDto
                )

            if (addAccountResult is Result.Success<Unit>) {

                onBoardingDto =
                    OnboardingDto(
                        authId = accountId,
                        accountId = accountId
                    )

                result = Result.Success(onBoardingDto)

            } else {

                val error = "account id was not generated in accounts collection"
                Logger.log(error)
                return Result.Failure(AccountResponseError.AccountIdNotGenerated(error))

            }
            //

        } catch (e: Exception) {
            val error = e.message.cleanString()
            Logger.log(error)
            return Result.Failure(AccountResponseError.Unknown(error))
        }

        return result
    }

    suspend fun loginAndRetrieveAccount(
        authDto: AuthDto
    ): Result<Account, AuthUseCaseError> {
        //login to firebase
        val result1 = authRepository.login(authDto)

        if(result1 is Result.Failure) {
            //return Result.Failure("error")
            return result1
        }

        val authenticationId = (result1 as Result.Success).data

        //retrieve accountdto and return to caller
        val result2 = accountRepository.fetchAccountByAccountId(
            accountId = authenticationId
        )

        if(result2 is Result.Failure) {
            //return Result.Failure("error")
            return result2
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
    ): Result<String, AuthUseCaseError> {

        return authRepository.login(
            authDto
        )
    }

    /**
     * Transit Method
     * No additional code
     */
    fun getUser(

    ): Result<UserDto, AuthUseCaseError>
    //FirebaseUser?
    {
        return authRepository.getUser()
    }
}