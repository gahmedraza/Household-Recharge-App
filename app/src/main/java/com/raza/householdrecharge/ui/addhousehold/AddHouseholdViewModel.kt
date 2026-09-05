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
import com.raza.householdrecharge.core.result.Result

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

            val result = householdUseCase.addHouseholdAndUpdateAccount(
                appUserDto = appUserDto,

                householdDto = householdDto
            )

            when(result) {
                is Result.Success<String> -> {

                    viewModelScope.launch {
                        sessionManager.saveHouseholdName(householdName)
                        sessionManager.saveHouseholdId(result.data)

                        onSuccess(result.data)
                    }
                }

                is Result.Failure<String> -> {

                    onFailure(result.error)
                }
            }
        }
    }
}