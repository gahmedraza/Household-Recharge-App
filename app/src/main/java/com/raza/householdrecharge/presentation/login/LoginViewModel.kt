package com.raza.householdrecharge.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.model.Account
import com.raza.householdrecharge.domain.usecase.AuthUseCase
import com.raza.householdrecharge.presentation.common.MobileNumberValidator
import com.raza.householdrecharge.presentation.common.PasswordValidator
import com.raza.householdrecharge.util.cleanString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val authUseCase: AuthUseCase
): ViewModel() {

    var loginUIState = MutableStateFlow(LoginUIState())

    fun login(
        onSuccess: (String?) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            //
            //validate the input fields
            loginUIState.update {
                it.copy(
                    mobileNumberError = MobileNumberValidator.validateMobileNumber(
                        loginUIState.value.mobileNumber,
                        loginUIState.value.mobileNumberError
                    )
                )
            }

            loginUIState.update {
                it.copy(
                    passwordError = PasswordValidator.validatePassword(
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
            //

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

            when(result51) {
                is Result.Success<Account> -> {
                    val accountDto = result51.data

                    sessionManager.saveUserId(accountDto.accountId.cleanString())

                    if(accountDto.householdId == null) {
                        sessionManager.saveHouseholdLinkStatus(false)
                    } else {
                        sessionManager.saveHouseholdLinkStatus(true)
                        sessionManager.saveHouseholdId(accountDto.householdId.cleanString())
                    }

                    loginUIState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    onSuccess(accountDto.accountId.cleanString())
                }
                is Result.Failure<String> -> {

                    loginUIState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    onFailure(result51.error)
                }
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