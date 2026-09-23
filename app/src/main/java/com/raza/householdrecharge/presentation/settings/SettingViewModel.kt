package com.raza.householdrecharge.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.raza.householdrecharge.data.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val sessionManager: SessionManager
): ViewModel() {

    var settingUIState = MutableStateFlow(SettingUIState())

    fun logout(onSuccess: () -> Unit, onFailure: () -> Unit) {
        FirebaseAuth
            .getInstance()
            .signOut()

        viewModelScope.launch(Dispatchers.IO) {
            sessionManager.clear()
        }

        onSuccess()
    }

    fun getName(onSuccess: (String) -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val name = sessionManager.memberName.first()

            if(name.isNullOrEmpty()) {
                onSuccess("Name is not set")
            } else {
                onSuccess(name)
            }
        }
    }

    fun getHouseholdName(onSuccess: (String) -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val name = sessionManager.householdName.first()

            if(name.isNullOrEmpty()) {
                onSuccess("Household is not set")
            } else {
                onSuccess(name)
            }
        }
    }

    fun getMobileNumber(onSuccess: (String) -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val name = sessionManager.mobileNumber.first()

            if(name.isNullOrEmpty()) {
                onSuccess("Household is not set")
            } else {
                onSuccess(name)
            }
        }
    }

    fun onShowBottomSheetModified(showBottomSheet: Boolean) {
        settingUIState.update {
            it.copy(
                showBottomSheet = showBottomSheet
            )
        }
    }
}