package com.raza.householdrecharge.domain.usecase

import com.raza.householdrecharge.common.log
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.data.remote.dto.OnboardingDto
import com.raza.householdrecharge.data.repository.AccountRepository
import com.raza.householdrecharge.data.repository.AuthRepository
import com.raza.householdrecharge.util.cleanString
import com.raza.householdrecharge.core.result.Result

class AuthUseCase(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository
) {

    suspend fun registerAndAddAccount(
        authDto: AuthDto,
        onSuccess: (OnboardingDto) -> Unit,
        onFailure: (String) -> Unit
    ) {
        try {

            val onBoardingDto: OnboardingDto

            log("a1")

            var accountDto: AccountDto? = null

            val signupResult = authRepository.register(authDto = authDto)

            var userId = ""

            when (signupResult) {
                is Result.Success -> {
                    log("a2")

                    userId = signupResult.s.cleanString()

                    accountDto = AccountDto(
                        accountName = authDto.accountName,
                        accountId = signupResult.s.cleanString()
                    )
                }

                is Result.Failure -> {
                    log("a3")

                    onFailure(signupResult.s.cleanString())
                    return
                    //onFailure("user id was not generated in user collection")
                }
            }

            if (accountDto == null) {
                onFailure("account cannot be added since account dto is empty")
                log("b1")
                return
            }

            log("b2")

            val addAccountResult = accountRepository.addAccount(accountDto = accountDto)

            if (addAccountResult is com.raza.householdrecharge.core.result.Result.Success<String>) {
                log("b3")
                onBoardingDto =
                    OnboardingDto(
                        authId = userId,
                        accountId = addAccountResult.s.cleanString()
                    )
                onSuccess(onBoardingDto)
            } else {
                log("b4")
                onFailure("account id was not generated in accounts collection")
                return
            }

            log("c")
            return

        } catch (e: Exception) {

            log("d")

            onFailure(e.message.cleanString())
            return
        }
    }

    suspend fun signinAndFetchAccount() {

    }
}