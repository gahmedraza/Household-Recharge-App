package com.raza.householdrecharge.v2.auth

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.v2.common.SessionManager
import com.raza.householdrecharge.v2.repository.AuthDto
import com.raza.householdrecharge.v2.repository.FirestoreRepository
import kotlinx.coroutines.launch

class SignInViewModel(
    private val sessionManager: SessionManager
) : AuthViewModel() {
    fun signIn(onSuccess: (String?) -> Unit, onFailure: (String?) -> Unit) {
        isLoading = true

        val authDto = AuthDto(
            mobileNumber = "$mobileNumber@householdrecharge.local",
            password = password
        )

        FirestoreRepository.signIn(
            authDto = authDto,

            onSuccess = { userId ->
                viewModelScope.launch {
                    sessionManager.saveUserId(userId)
                }

                this.authId = userId

                isLoading = false
                onSuccess(userId)
            },

            onFailure = { error ->

                isLoading = false
                onFailure(error)
            }
        )
    }
}