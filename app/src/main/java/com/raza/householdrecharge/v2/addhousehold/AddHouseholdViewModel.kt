package com.raza.householdrecharge.v2.addhousehold

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.v2.BaseViewModel
import com.raza.householdrecharge.v2.common.HouseholdDto
import com.raza.householdrecharge.v2.SessionManager
import com.raza.householdrecharge.v2.repository.AppUserDto
import com.raza.householdrecharge.v2.repository.FirestoreRepository
import com.raza.householdrecharge.v2.repository.cleanString
import kotlinx.coroutines.launch

class AddHouseholdViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {

    fun onAddHousehold(
        userId: String?,
        householdName: String,
        onSuccess: (String?) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            val userNotFound = userId?.isEmpty() ?: false

            if (userNotFound) {
                onFailure("user not found")
                return@launch
            }

            val householdDto = HouseholdDto(
                name = householdName
            )

            val appUserDto = AppUserDto(
                userId = userId.cleanString(),
                householdId = ""
            )

            FirestoreRepository.addHousehold(
                appUserDto = appUserDto,

                householdDto = householdDto,

                onSuccess = { householdId ->

                    viewModelScope.launch {
                        sessionManager.saveHouseholdName(householdName)
                        sessionManager.saveHouseholdId(householdId)

                        onSuccess(householdId)
                    }
                },

                onFailure = { error ->

                    onFailure(error)
                }
            )
        }
    }
}