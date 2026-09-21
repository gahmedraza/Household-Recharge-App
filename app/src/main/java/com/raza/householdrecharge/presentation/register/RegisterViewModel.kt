package com.raza.householdrecharge.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.data.remote.dto.OnboardingDto
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.error.AccountError
import com.raza.householdrecharge.domain.usecase.AuthUseCase
import com.raza.householdrecharge.util.cleanString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val authUseCase: AuthUseCase
) : ViewModel() {

    var registerUIState = MutableStateFlow(RegisterUIState())

    fun registerAndAddAccount(onSuccess: (String?) -> Unit, onFailure: (String?) -> Unit) {
        viewModelScope.launch {
            registerUIState.update {
                it.copy(
                    isLoading = true
                )
            }

            val authDto = AuthDto(
                accountName = registerUIState.value.accountName,
                mobileNumber = "${registerUIState.value.mobileNumber}@householdrecharge.local",
                password = registerUIState.value.password
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
                        sessionManager.saveMobileNumber(registerUIState.value.mobileNumber)
                    }

                    registerUIState.value.authId = onBoardingDto.authId.cleanString()
                    registerUIState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    onSuccess(onBoardingDto.authId.cleanString())
                }

                is Result.Failure<AccountError> -> {

                    registerUIState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    onFailure(result.error.toString())//todo modify
                }
            }
        }
    }

    fun onAccountNameChanged(accountName: String) {
        registerUIState.update {
            it.copy(
                accountName = accountName
            )
        }
    }

    fun onMobileNumberChanged(mobileNumber: String) {
        registerUIState.update {
            it.copy(
                mobileNumber = mobileNumber
            )
        }
    }

    fun onPasswordChanged(password: String) {
        registerUIState.update {
            it.copy(
                password = password
            )
        }
    }
}