package com.raza.householdrecharge.ui.splash

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.data.repository.HouseholdRepository
import com.raza.householdrecharge.ui.auth.AuthViewModel
import kotlinx.coroutines.launch
import com.raza.householdrecharge.core.result.Result

class SplashViewModel(
    private val sessionManager: SessionManager,
    private val householdRepository: HouseholdRepository
) : AuthViewModel(sessionManager) {

    fun loadHousehold(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {

        viewModelScope.launch {

            val result = householdRepository.fetchHousehold(
            )

            when(result){

                is Result.Success<String> -> {

                    household.id = result.data

                    viewModelScope.launch {
                        sessionManager.saveHouseholdId(result.data)
                    }

                    onSuccess()
                }

                is Result.Failure<String> -> {

                    onFailure(result.error)
                }
            }
        }
    }
}