package com.raza.householdrecharge.v2.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.v2.common.BaseViewModel
import com.raza.householdrecharge.v2.common.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

open class AuthViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {

    var authId by mutableStateOf("")

    fun getOnboardingStatus(): Boolean {
        var onboardingStatus: Boolean = false

        viewModelScope.launch {
            onboardingStatus = sessionManager.isOnboardingComplete.first()
        }

        return onboardingStatus
    }

    fun setOnboardingStatus(authFlowStatus: Boolean) {
        viewModelScope.launch {
            sessionManager.saveOnBoardingStatus(authFlowStatus)
        }
    }

    fun isOnboardingComplete() = getOnboardingStatus()
}