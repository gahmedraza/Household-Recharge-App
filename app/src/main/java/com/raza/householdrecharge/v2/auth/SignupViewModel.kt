package com.raza.householdrecharge.v2.auth

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.v2.SessionManager
import com.raza.householdrecharge.v2.repository.FirestoreRepository
import com.raza.householdrecharge.v2.repository.AuthDto
import kotlinx.coroutines.launch

class SignupViewModel(
    private val sessionManager: SessionManager
) : AuthViewModel() {
    fun signupAndAccount(onSuccess: (String?) -> Unit, onFailure: (String?) -> Unit) {
        viewModelScope.launch {
            isLoading = true

            val authDto = AuthDto(
                username = name,
                mobileNumber = "$mobileNumber@householdrecharge.local",
                password = password
            )

            FirestoreRepository.signupAndAddAccount(
                authDto,

                onSuccess = { uId ->
                    viewModelScope.launch {
                        sessionManager.saveUserId(uId)
                        sessionManager.saveMobileNumber(mobileNumber)
                    }

                    userId = uId
                    isLoading = false
                    onSuccess(uId)
                },

                onFailure = { error ->

                    isLoading = false
                    onFailure(error)
                })
        }
    }
}