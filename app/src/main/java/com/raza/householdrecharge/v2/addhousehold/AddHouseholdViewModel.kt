package com.raza.householdrecharge.v2.addhousehold

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.v2.common.BaseViewModel
import com.raza.householdrecharge.v2.common.HouseholdDto
import com.raza.householdrecharge.v2.common.SessionManager
import com.raza.householdrecharge.v2.data.dto.*
import com.raza.householdrecharge.v2.repository.FirestoreRepository
import com.raza.householdrecharge.v2.util.cleanString
import kotlinx.coroutines.flow.first
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
            val accountId = sessionManager.accountId.first()

            if (userNotFound) {
                onFailure("user not found")
                return@launch
            }

            val householdDto = HouseholdDto(
                householdName = householdName
            )

            val appUserDto = AppUserDto(
                authId = userId.cleanString(),
                accountId = accountId.cleanString(),
                householdId = ""
            )

            FirestoreRepository.addHouseholdAndUpdateAccount(
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