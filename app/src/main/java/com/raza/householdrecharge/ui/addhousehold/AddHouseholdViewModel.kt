package com.raza.householdrecharge.ui.addhousehold

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.common.BaseViewModel
import com.raza.householdrecharge.common.HouseholdDto
import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.domain.usecase.HouseholdUseCase
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddHouseholdViewModel(
    private val sessionManager: SessionManager,
    private val householdUseCase: HouseholdUseCase
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

            householdUseCase.addHouseholdAndUpdateAccount(
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