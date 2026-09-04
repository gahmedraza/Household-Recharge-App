package com.raza.householdrecharge.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sessionManager: SessionManager,
    private val authRepository: AuthRepository
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

    fun login(
        authDto: AuthDto,
        onSuccess: (String?) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        isLoading = true

        val authDto = AuthDto(
            mobileNumber = "${authDto.mobileNumber}@householdrecharge.local",
            password = authDto.password
        )

        authRepository.login(
            authDto = authDto,

            onSuccess = { userId ->
                viewModelScope.launch {
                    sessionManager.saveUserId(userId)
                }

                //this.authId = userId todo delete

                isLoading = false
                onSuccess(userId)
            },

            onFailure = { error ->

                isLoading = false
                onFailure(error)
            }
        )
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