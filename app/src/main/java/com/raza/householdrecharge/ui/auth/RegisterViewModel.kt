package com.raza.householdrecharge.ui.auth

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.data.remote.dto.OnboardingDto
import com.raza.householdrecharge.domain.usecase.AuthUseCase
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.launch
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.repository.account.AccountError

class RegisterViewModel(
    private val sessionManager: SessionManager,
    private val authUseCase: AuthUseCase
) : AuthViewModel(sessionManager) {
    fun registerAndAddAccount(onSuccess: (String?) -> Unit, onFailure: (String?) -> Unit) {
        viewModelScope.launch {
            isLoading = true

            val authDto = AuthDto(
                accountName = accountName,
                mobileNumber = "$mobileNumber@householdrecharge.local",
                password = password
            )

            val result = authUseCase.registerAndCreateAccount(
                authDto
            )

            when(result) {
                is Result.Success<OnboardingDto> -> {

                    val onBoardingDto = result.data

                    viewModelScope.launch {
                        sessionManager.saveUserId(onBoardingDto.authId.cleanString())
                        sessionManager.saveAccountId(onBoardingDto.accountId.cleanString())
                        sessionManager.saveMobileNumber(mobileNumber)
                    }

                    authId = onBoardingDto.authId.cleanString()
                    isLoading = false
                    onSuccess(onBoardingDto.authId.cleanString())
                }

                is Result.Failure<AccountError> -> {

                    isLoading = false
                    onFailure(result.error.toString())//todo modify
                }
            }
        }
    }
}