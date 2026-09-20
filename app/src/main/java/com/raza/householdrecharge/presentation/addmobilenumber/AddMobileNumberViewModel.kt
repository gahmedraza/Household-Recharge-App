package com.raza.householdrecharge.presentation.addmobilenumber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.factory.MobileNumberDtoFactory
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.MobileNumberUseCase
import com.raza.householdrecharge.domain.validator.MobileNumberRequestValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMobileNumberViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val mobileNumberUseCase: MobileNumberUseCase,
    private val requestValidator: MobileNumberRequestValidator
) : ViewModel() {

    var addMobileNumberUIState = MutableStateFlow(AddMobileNumberUIState())

    fun addMobileNumber(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            addMobileNumberUIState.update {
                it.copy(
                    isLoading = true
                )
            }
            //
            val mobileNumberDto = MobileNumberDtoFactory(
                sessionManager = sessionManager
            ).create(
                mobileNumber = addMobileNumberUIState.value.mobileNumber.toLong()
            )

            val validationResult = requestValidator.validate(
                userId = sessionManager.authId.first(),
                householdId = sessionManager.householdId.first(),
                mobileNumber = addMobileNumberUIState.value.mobileNumber
            )

            //user understandable errors should be placed in a class
            if (validationResult is Result.Failure) {
                onFailure(validationResult.error.toString())
                addMobileNumberUIState.update {
                    it.copy(
                        isLoading = false
                    )
                }
                return@launch
            }
            //

            val result = mobileNumberUseCase.addMobileNumber(
                mobileNumberDto = mobileNumberDto
            )

            when (result) {

                is Result.Success<String> -> {
                    addMobileNumberUIState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    onSuccess()
                }

                is Result.Failure<String> -> {
                    addMobileNumberUIState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    onFailure(result.error)
                }
            }
        }
    }
}