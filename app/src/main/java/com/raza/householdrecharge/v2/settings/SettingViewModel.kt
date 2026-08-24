package com.raza.householdrecharge.v2.settings

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.raza.householdrecharge.v2.BaseViewModel
import com.raza.householdrecharge.v2.SessionManager
import kotlinx.coroutines.launch

class SettingViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {

    fun signOut(onSuccess: () -> Unit, onFailure: () -> Unit) {
        FirebaseAuth
            .getInstance()
            .signOut()

        viewModelScope.launch {
            sessionManager.clear()
        }

        onSuccess()
    }

}