package com.raza.householdrecharge.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.data.remote.dto.OnboardingDto
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.error.response.AccountResponseError
import com.raza.householdrecharge.domain.error.response.ResponseError
import com.raza.householdrecharge.domain.usecase.AuthUseCase
import com.raza.householdrecharge.presentation.common.MobileNumberValidator
import com.raza.householdrecharge.presentation.common.PasswordValidator
import com.raza.householdrecharge.presentation.common.AccountNameValidator
import com.raza.householdrecharge.presentation.error.RequestErrorMapper
import com.raza.householdrecharge.presentation.error.ResponseErrorMapper
import com.raza.householdrecharge.util.cleanString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val authUseCase: AuthUseCase,
    private val accountNameValidator: AccountNameValidator,
    private val mobileNumberValidator: MobileNumberValidator,
    private val passwordValidator: PasswordValidator,
    private val responseErrorMapper: ResponseErrorMapper,
) : ViewModel() {

    var registerUIState = MutableStateFlow(RegisterUIState())

    fun registerAndAddAccount(
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            //validate the input fields
            registerUIState.update {
                it.copy(
                    accountNameError = accountNameValidator.validate(
                        registerUIState.value.accountName,
                        registerUIState.value.accountNameError
                    ),

                    mobileNumberError = mobileNumberValidator.validate(
                        registerUIState.value.mobileNumber,
                        registerUIState.value.mobileNumberError
                    ),

                    passwordError = passwordValidator.validate(
                        registerUIState.value.password,
                        registerUIState.value.passwordError
                    )
                )
            }

            if(registerUIState.value.accountNameError.isNotEmpty()
                || registerUIState.value.mobileNumberError.isNotEmpty()
                || registerUIState.value.passwordError.isNotEmpty()
            ) {

                return@launch
            }

            //isLoading intentionally placed after validation
            registerUIState.update {
                it.copy(
                    isLoading = true
                )
            }

            //todo factory required
            val authDto = AuthDto(
                accountName = registerUIState.value.accountName,
                mobileNumber = "${registerUIState.value.mobileNumber}@householdrecharge.local",
                password = registerUIState.value.password
            )

            val result = authUseCase.registerAndCreateAccount(
                authDto
            )

            when(result) {
                is Result.Success -> {

                    val onBoardingDto = result.data

                    viewModelScope.launch(Dispatchers.IO) {
                        sessionManager.saveUserId(onBoardingDto.authId.cleanString())
                        sessionManager.saveAccountId(onBoardingDto.accountId.cleanString())
                        sessionManager.saveMobileNumber(registerUIState.value.mobileNumber)
                    }

                    registerUIState.value.authId = onBoardingDto.authId.cleanString()

                    registerUIState.update {
                        it.copy(
                            apiResponse = "account creation success",
                            shouldProceed = true,
                            isLoading = false
                        )
                    }
                }

                is Result.Failure -> {

                    registerUIState.update {
                        it.copy(
                            apiResponse = "account creation failure ${responseErrorMapper.map(result.error)}",
                            isLoading = false
                        )
                    }
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

    fun resetAccountNameError() {
        registerUIState.update {
            it.copy(
                accountNameError = ""
            )
        }
    }

    fun resetMobileNumberError() {
        registerUIState.update {
            it.copy(
                mobileNumberError = ""
            )
        }
    }

    fun resetPasswordError() {
        registerUIState.update {
            it.copy(
                passwordError = ""
            )
        }
    }
}