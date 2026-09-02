package com.raza.householdrecharge.v2.auth

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.v2.common.SessionManager
import com.raza.householdrecharge.v2.repository.FirestoreRepository
import com.raza.householdrecharge.v2.data.dto.*
import com.raza.householdrecharge.v2.util.cleanString
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