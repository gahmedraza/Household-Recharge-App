package com.raza.householdrecharge.v2.auth

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.v2.common.SessionManager
import com.raza.householdrecharge.v2.data.dto.*
import com.raza.householdrecharge.v2.repository.FirestoreRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sessionManager: SessionManager
) : AuthViewModel(sessionManager) {
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

        FirestoreRepository.login(
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