package com.raza.householdrecharge.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.presentation.common.BaseViewModel
import com.raza.householdrecharge.presentation.theme.ThemeMode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {

    var themeMode by mutableStateOf(ThemeMode.SYSTEM)

    fun signOut(onSuccess: () -> Unit, onFailure: () -> Unit) {
        FirebaseAuth
            .getInstance()
            .signOut()

        viewModelScope.launch {
            sessionManager.clear()
        }

        onSuccess()
    }

    fun getName(onSuccess: (String) -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            val name = sessionManager.memberName.first()

            if(name.isNullOrEmpty()) {
                onSuccess("Name is not set")
            } else {
                onSuccess(name)
            }
        }
    }

    fun getHouseholdName(onSuccess: (String) -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            val name = sessionManager.householdName.first()

            if(name.isNullOrEmpty()) {
                onSuccess("Household is not set")
            } else {
                onSuccess(name)
            }
        }
    }

    fun getMobileNumber(onSuccess: (String) -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            val name = sessionManager.mobileNumber.first()

            if(name.isNullOrEmpty()) {
                onSuccess("Household is not set")
            } else {
                onSuccess(name)
            }
        }
    }
}