package com.raza.householdrecharge.ui.auth

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.util.cleanString
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.repository.FirestoreRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val sessionManager: SessionManager
) : AuthViewModel(sessionManager) {
    fun registerAndAddAccount(onSuccess: (String?) -> Unit, onFailure: (String?) -> Unit) {
        viewModelScope.launch {
            isLoading = true

            val authDto = AuthDto(
                accountName = accountName,
                mobileNumber = "$mobileNumber@householdrecharge.local",
                password = password
            )

            FirestoreRepository.registerAndAddAccount(
                authDto,

                onSuccess = { onBoardingDto ->
                    viewModelScope.launch {
                        sessionManager.saveUserId(onBoardingDto.authId.cleanString())
                        sessionManager.saveAccountId(onBoardingDto.accountId.cleanString())
                        sessionManager.saveMobileNumber(mobileNumber)
                    }

                    authId = onBoardingDto.authId.cleanString()
                    isLoading = false
                    onSuccess(onBoardingDto.authId.cleanString())
                },

                onFailure = { error ->

                    isLoading = false
                    onFailure(error)
                })
        }
    }
}