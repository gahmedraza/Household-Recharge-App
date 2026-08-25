package com.raza.householdrecharge.v2.auth

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.raza.householdrecharge.v2.SessionManager
import com.raza.householdrecharge.v2.repository.AuthDto
import com.raza.householdrecharge.v2.repository.FirestoreRepository
import kotlinx.coroutines.launch

class SignInViewModel(
    private val sessionManager: SessionManager
) : AuthViewModel() {
    fun signIn(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {
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

                this.userId = userId

                isLoading = false
                onSuccess()
            },

            onFailure = { error ->

                isLoading = false
                onFailure(error)
            }
        )
    }
}