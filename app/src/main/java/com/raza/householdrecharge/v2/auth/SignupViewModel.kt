package com.raza.householdrecharge.v2.auth

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.v2.SessionManager
import com.raza.householdrecharge.v2.repository.FirestoreRepository
import com.raza.householdrecharge.v2.repository.AuthDto
import com.raza.householdrecharge.v2.repository.cleanString
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

                onSuccess = { onBoardingDto ->
                    viewModelScope.launch {
                        sessionManager.saveUserId(onBoardingDto.userId.cleanString())
                        sessionManager.saveAccountId(onBoardingDto.accountId.cleanString())
                        sessionManager.saveMobileNumber(mobileNumber)
                    }

                    userId = onBoardingDto.userId.cleanString()
                    isLoading = false
                    onSuccess(onBoardingDto.userId.cleanString())
                },

                onFailure = { error ->

                    isLoading = false
                    onFailure(error)
                })
        }
    }
}