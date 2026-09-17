package com.raza.householdrecharge.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.data.remote.dto.OnboardingDto
import com.raza.householdrecharge.domain.usecase.AuthUseCase
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.launch
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.AccountError
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val authUseCase: AuthUseCase
) : ViewModel() {

    var registerUIState by mutableStateOf(RegisterUIState())

    fun registerAndAddAccount(onSuccess: (String?) -> Unit, onFailure: (String?) -> Unit) {
        viewModelScope.launch {
            registerUIState.isLoading = true

            val authDto = AuthDto(
                accountName = registerUIState.accountName,
                mobileNumber = "${registerUIState.mobileNumber}@householdrecharge.local",
                password = registerUIState.password
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
                        sessionManager.saveMobileNumber(registerUIState.mobileNumber)
                    }

                    registerUIState.authId = onBoardingDto.authId.cleanString()
                    registerUIState.isLoading = false
                    onSuccess(onBoardingDto.authId.cleanString())
                }

                is Result.Failure<AccountError> -> {

                    registerUIState.isLoading = false
                    onFailure(result.error.toString())//todo modify
                }
            }
        }
    }
}