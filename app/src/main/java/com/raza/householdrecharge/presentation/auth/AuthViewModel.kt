package com.raza.householdrecharge.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AuthViewModel @Inject constructor(
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