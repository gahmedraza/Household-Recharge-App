package com.raza.householdrecharge.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.model.Account
import com.raza.householdrecharge.domain.usecase.AuthUseCase
import com.raza.householdrecharge.domain.usecase.HouseholdUseCase
import com.raza.householdrecharge.presentation.common.MobileNumberValidator
import com.raza.householdrecharge.presentation.common.PasswordValidator
import com.raza.householdrecharge.presentation.error.RequestErrorMapper
import com.raza.householdrecharge.presentation.error.ResponseErrorMapper
import com.raza.householdrecharge.util.cleanString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val authUseCase: AuthUseCase,
    private val householdUseCase: HouseholdUseCase,
    private val mobileNumberValidator: MobileNumberValidator,
    private val passwordValidator: PasswordValidator,
    private val responseErrorMapper: ResponseErrorMapper,
): ViewModel() {

    var loginUIState = MutableStateFlow(LoginUIState())

    fun login(
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            //validate the input fields
            loginUIState.update {
                it.copy(
                    mobileNumberError = mobileNumberValidator.validate(
                        loginUIState.value.mobileNumber,
                        loginUIState.value.mobileNumberError
                    ),

                    passwordError = passwordValidator.validate(
                        loginUIState.value.password,
                        loginUIState.value.passwordError
                    )
                )
            }

            if(loginUIState.value.mobileNumberError.isNotEmpty()
                || loginUIState.value.passwordError.isNotEmpty()
                ) {

                return@launch
            }

            //isLoading intentionally placed after validation
            loginUIState.update {
                it.copy(
                    isLoading = true
                )
            }

            val authDto = AuthDto(
                mobileNumber = "${loginUIState.value.mobileNumber}@householdrecharge.local",
                password = loginUIState.value.password
            )

            val result51 = authUseCase.loginAndRetrieveAccount(
                authDto = authDto
            )

            if (result51 is Result.Failure) {

                loginUIState.update {
                    it.copy(
                        apiResponse = "account login failure\n${responseErrorMapper.map(result51.error)}",
                        isLoading = false,
                        shouldProceed = false
                    )
                }

                return@launch
            }

            val accountDto = (result51 as Result.Success).data

            //
            val result54 = householdUseCase.getHouseholdByHouseholdId(
                accountDto.householdId.cleanString()
            )

            when(result54) {
                is Result.Success -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        sessionManager.saveHouseholdName(result54.data.householdName.cleanString())
                        sessionManager.saveHouseholdId(result54.data.householdId.cleanString())
                    }
                }

                is Result.Failure -> {
                    //todo
                }
            }
            //

            sessionManager.saveUserId(accountDto.accountId.cleanString())

            if (accountDto.householdId == null) {
                sessionManager.saveHouseholdLinkStatus(false)
            } else {
                sessionManager.saveHouseholdLinkStatus(true)
                //sessionManager.saveHouseholdId(accountDto.householdId.cleanString())
            }

            loginUIState.update {
                it.copy(
                    apiResponse = "account login success",
                    isLoading = false,
                    shouldProceed = true
                )
            }

        }
    }

    fun loginAndFetchAccount() {
        //user provides mobile number and password
        //login using firebase auth
        //when login is done you receive authid from firebase auth collection
        //fetch account using fire store
        //save the details in the session manager
        //allow user to proceed
    }

    suspend fun isOnboardingComplete() = sessionManager.isUserLinkedToAHousehold.first()

    fun onMobileNumberChanged(mobileNumber: String) {
        loginUIState.update {
            it.copy(
                mobileNumber = mobileNumber
            )
        }
    }

    fun resetMobileNumberError() {
        loginUIState.update {
            it.copy(
                mobileNumberError = ""
            )
        }
    }

    fun onPasswordChanged(password: String) {
        loginUIState.update {
            it.copy(
                password = password
            )
        }
    }

    fun resetPasswordError() {
        loginUIState.update {
            it.copy(
                passwordError = ""
            )
        }
    }
}