package com.raza.householdrecharge.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.data.repository.AuthRepository
import kotlinx.coroutines.launch
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.domain.usecase.AuthUseCase
import com.raza.householdrecharge.util.cleanString

class LoginViewModel(
    private val sessionManager: SessionManager,
    private val authRepository: AuthRepository,
    private val authUseCase: AuthUseCase
) : AuthViewModel(sessionManager) {

    var uiState by mutableStateOf(LoginUIState())
        private set

    private fun setPhoneNumber(value: String) {
        uiState = uiState.copy(
            mobileNumber = value
        )
    }

    @Override
    private fun setPasswordKey(value: String) {
        uiState = uiState.copy(
            password = value
        )
    }

    private fun setMobileNumberError(value: String) {
        uiState = uiState.copy(
            mobileNumberError = value
        )
    }

    private fun setPasswordError(value: String) {
        uiState = uiState.copy(
            passwordError = value
        )
    }

    fun login2(
        authDto: AuthDto,
        onSuccess: (String?) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true

            val authDto = AuthDto(
                mobileNumber = "${authDto.mobileNumber}@householdrecharge.local",
                password = authDto.password
            )

            val result51 = authUseCase.loginAndRetrieveAccount(
                authDto = authDto
            )

            when(result51) {
                is Result.Success<AccountDto> -> {

                    sessionManager.saveUserId(result51.data.accountId.cleanString())
                    if(result51.data.householdId == null) {
                        sessionManager.saveHouseholdLinkStatus(false)
                    } else {
                        sessionManager.saveHouseholdLinkStatus(true)
                    }

                    isLoading = false
                    onSuccess(result51.data.accountId.cleanString())
                }
                is Result.Failure<String> -> {

                    isLoading = false
                    onFailure(result51.error)
                }
            }

        }
    }

    fun login(
        authDto: AuthDto,
        onSuccess: (String?) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true

            val authDto = AuthDto(
                mobileNumber = "${authDto.mobileNumber}@householdrecharge.local",
                password = authDto.password
            )

            val result = authRepository.login(
                authDto = authDto
            )

            when(result) {
                is Result.Success<String> -> {

                    sessionManager.saveUserId(result.data)
                    isLoading = false
                    onSuccess(result.data)
                }
                is Result.Failure<String> -> {

                    isLoading = false
                    onFailure(result.error)
                }
            }

        }
    }

    fun signinAndFetchAccount() {
        //user provides mobile number and password
        //signin using firebase auth
        //when signin is done you receive authid from firebase auth collection
        //fetch account using fire store
        //save the details in the session manager
        //allow user to proceed
    }
}

data class LoginUIState(
    val mobileNumber: String = "",
    val password: String = "",
    val mobileNumberError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false
)