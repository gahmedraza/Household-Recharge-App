package com.raza.householdrecharge.v2.splash

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.v2.common.BaseViewModel
import com.raza.householdrecharge.v2.common.SessionManager
import com.raza.householdrecharge.v2.repository.FirestoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {

    fun loadHousehold(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {

        FirestoreRepository.fetchHousehold(
            onSuccess = { householdId ->

                household.id = householdId

                viewModelScope.launch {
                    sessionManager.saveHouseholdId(householdId)
                }

                onSuccess()
            },
            onFailure = { error ->

                onFailure(error)
            }
        )
    }

    fun getAuthFlowStatus(): Boolean {
        var authFlowStatus: Boolean = false

        viewModelScope.launch {
            authFlowStatus = sessionManager.isAuthFlowComplete.first()
        }

        return authFlowStatus
    }

    fun setAuthFlowStatus(authFlowStatus: Boolean) {
        viewModelScope.launch {
            sessionManager.saveIsAuthFlowComplete(authFlowStatus)
        }
    }
}