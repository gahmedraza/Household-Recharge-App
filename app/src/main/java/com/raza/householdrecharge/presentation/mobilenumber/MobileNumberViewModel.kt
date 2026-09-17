package com.raza.householdrecharge.presentation.mobilenumber

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.factory.MobileNumberDtoFactory
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.MobileNumberUseCase
import com.raza.householdrecharge.domain.validator.MobileNumberValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MobileNumberViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val mobileNumberUseCase: MobileNumberUseCase,
    private val validator: MobileNumberValidator
) : ViewModel() {

    var mobileNumberUIState by mutableStateOf(MobileNumberUIState())

    fun addMobileNumber(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            mobileNumberUIState.isLoading = true
            //
            val mobileNumberDto = MobileNumberDtoFactory(
                sessionManager = sessionManager
            ).create(
                mobileNumber = mobileNumberUIState.mobileNumber.toLong()
            )

            val validationResult = validator.validate(
                userId = sessionManager.authId.first(),
                householdId = sessionManager.householdId.first(),
                mobileNumber = mobileNumberUIState.mobileNumber
            )

            //user understandable errors should be placed in a class
            if (validationResult is Result.Failure) {
                onFailure(validationResult.error.toString())
                mobileNumberUIState.isLoading = false
                return@launch
            }
            //

            val result = mobileNumberUseCase.addMobileNumber(
                mobileNumberDto = mobileNumberDto
            )

            when (result) {

                is Result.Success<String> -> {
                    mobileNumberUIState.isLoading = false
                    onSuccess()
                }

                is Result.Failure<String> -> {
                    mobileNumberUIState.isLoading = false
                    onFailure(result.error)
                }
            }
        }
    }
}