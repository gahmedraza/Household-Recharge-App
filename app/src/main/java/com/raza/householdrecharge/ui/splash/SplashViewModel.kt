package com.raza.householdrecharge.ui.splash

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.ui.auth.AuthViewModel
import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.repository.FirestoreRepository
import kotlinx.coroutines.launch

class SplashViewModel(
    private val sessionManager: SessionManager
) : AuthViewModel(sessionManager) {

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
}