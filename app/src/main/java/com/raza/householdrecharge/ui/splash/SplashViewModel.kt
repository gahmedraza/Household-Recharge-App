package com.raza.householdrecharge.ui.splash

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.data.repository.HouseholdRepository
import com.raza.householdrecharge.ui.auth.AuthViewModel
import kotlinx.coroutines.launch

class SplashViewModel(
    private val sessionManager: SessionManager,
    private val householdRepository: HouseholdRepository
) : AuthViewModel(sessionManager) {

    fun loadHousehold(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {

        householdRepository.fetchHousehold(
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