package com.raza.householdrecharge.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.AuthUseCase
import com.raza.householdrecharge.presentation.common.MobileNumberValidator
import com.raza.householdrecharge.presentation.common.PasswordValidator
import com.raza.householdrecharge.util.cleanString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val authUseCase: AuthUseCase
): ViewModel() {

    var loginUIState by mutableStateOf(LoginUIState())

    fun login(
        onSuccess: (String?) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            //
            //validate the input fields
            loginUIState.mobileNumberError = MobileNumberValidator.validateMobileNumber(
                loginUIState.mobileNumber,
                loginUIState.mobileNumberError
            )

            loginUIState.passwordError = PasswordValidator.validatePassword(
                loginUIState.password,
                loginUIState.passwordError
            )

            if(loginUIState.mobileNumberError.isNotEmpty()
                || loginUIState.passwordError.isNotEmpty()
                ) {

                return@launch
            }
            //

            loginUIState.isLoading = true

            val authDto = AuthDto(
                mobileNumber = "${loginUIState.mobileNumber}@householdrecharge.local",
                password = loginUIState.password
            )

            val result51 = authUseCase.loginAndRetrieveAccount(
                authDto = authDto
            )

            when(result51) {
                is Result.Success<AccountDto> -> {
                    val accountDto = result51.data

                    sessionManager.saveUserId(accountDto.accountId.cleanString())

                    if(accountDto.householdId == null) {
                        sessionManager.saveHouseholdLinkStatus(false)
                    } else {
                        sessionManager.saveHouseholdLinkStatus(true)
                        sessionManager.saveHouseholdId(accountDto.householdId.cleanString())
                    }

                    loginUIState.isLoading = false
                    onSuccess(accountDto.accountId.cleanString())
                }
                is Result.Failure<String> -> {

                    loginUIState.isLoading = false
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
}