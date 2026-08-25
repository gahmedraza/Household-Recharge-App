package com.raza.householdrecharge.v2.splash

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.v2.BaseViewModel
import com.raza.householdrecharge.v2.SessionManager
import com.raza.householdrecharge.v2.repository.FirestoreRepository
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
}