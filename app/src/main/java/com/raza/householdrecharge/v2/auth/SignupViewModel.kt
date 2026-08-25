package com.raza.householdrecharge.v2.auth

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.v2.SessionManager
import com.raza.householdrecharge.v2.repository.FirestoreRepository
import com.raza.householdrecharge.v2.repository.AuthDto
import kotlinx.coroutines.launch

class SignupViewModel(
    private val sessionManager: SessionManager
) : AuthViewModel() {
    fun signup(onSuccess: (String?) -> Unit, onFailure: (String?) -> Unit) {
        isLoading = true

        val authDto = AuthDto(
            mobileNumber = "$mobileNumber@householdrecharge.local",
            password = password
        )

        FirestoreRepository.signup(
            authDto,

            onSuccess = { userId ->
                viewModelScope.launch {
                    sessionManager.saveUserId(userId)
                    sessionManager.saveMobileNumber(mobileNumber)
                }

                this.userId = userId
                isLoading = false
                onSuccess(userId)
            },

            onFailure = { error ->

                isLoading = false
                onFailure(error)
            })
    }
}