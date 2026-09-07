package com.raza.householdrecharge.ui.mobilenumber

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.common.BaseViewModel
import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.factory.AppUserDtoFactory
import com.raza.householdrecharge.data.remote.factory.MobileNumberDtoFactory
import com.raza.householdrecharge.data.remote.factory.RechargeDtoFactory
import com.raza.householdrecharge.data.repository.MobileNumberRepository
import com.raza.householdrecharge.domain.validator.MobileNumberValidator
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MobileNumberViewModel(
    private val sessionManager: SessionManager,
    private val mobileNumberRepository: MobileNumberRepository,
    private val validator: MobileNumberValidator
) : BaseViewModel() {

    var mobileNumber2 by mutableStateOf("")

    fun addMobileNumber(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            //
            val mobileNumberDto = MobileNumberDtoFactory(
                sessionManager = sessionManager
            ).create(
                mobileNumber = mobileNumber2.toLong()
            )

            val validationResult = validator.validate(
                userId = sessionManager.authId.first(),
                householdId = sessionManager.householdId.first(),
                mobileNumber = mobileNumber2
            )

            //user understandable errors should be placed in a class
            if (validationResult is Result.Failure) {
                onFailure(validationResult.error.toString())
                isLoading = false
                return@launch
            }
            //

            val result = mobileNumberRepository.addMobileNumber(
                mobileNumberDto = mobileNumberDto
            )

            when (result) {

                is Result.Success<String> -> {
                    isLoading = false
                    onSuccess()
                }

                is Result.Failure<String> -> {
                    isLoading = false
                    onFailure(result.error)
                }
            }
        }
    }
}