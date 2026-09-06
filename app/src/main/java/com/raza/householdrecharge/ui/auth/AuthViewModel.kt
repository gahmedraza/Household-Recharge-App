package com.raza.householdrecharge.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.common.BaseViewModel
import com.raza.householdrecharge.common.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

open class AuthViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {

    var password by mutableStateOf("")
    var authId by mutableStateOf("")

    fun setHouseholdLinkStatus(authFlowStatus: Boolean) {
        viewModelScope.launch {

            sessionManager.saveHouseholdLinkStatus(authFlowStatus)
        }
    }

    suspend fun isOnboardingComplete() = sessionManager.isUserLinkedToAHousehold.first()
}