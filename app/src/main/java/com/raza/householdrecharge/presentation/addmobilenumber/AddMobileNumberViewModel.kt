package com.raza.householdrecharge.presentation.addmobilenumber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.factory.MobileNumberDtoFactory
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.MobileNumberUseCase
import com.raza.householdrecharge.domain.validator.request.MobileNumberRequestValidator
import com.raza.householdrecharge.presentation.common.MobileNumberValidator
import com.raza.householdrecharge.presentation.error.RequestErrorMapper
import com.raza.householdrecharge.presentation.error.ResponseErrorMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMobileNumberViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val mobileNumberUseCase: MobileNumberUseCase,
    private val requestValidator: MobileNumberRequestValidator,
    private val mobileNumberValidator: MobileNumberValidator,
    private val responseErrorMapper: ResponseErrorMapper,
    private val requestErrorMapper: RequestErrorMapper
) : ViewModel() {

    var addMobileNumberUIState = MutableStateFlow(AddMobileNumberUIState())

    fun addMobileNumber(
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            addMobileNumberUIState.update {
                it.copy(
                    isLoading = true
                )
            }

            //validate the input fields
            addMobileNumberUIState.update {
                it.copy(
                    mobileNumberError = mobileNumberValidator.validate(
                        addMobileNumberUIState.value.mobileNumber,
                        addMobileNumberUIState.value.mobileNumberError
                    )
                )
            }

            if(addMobileNumberUIState.value.mobileNumberError.isNotEmpty()
            ) {
                //TODO do not add api error
                //TODO api error only meant for api related
                return@launch
            }

            val mobileNumberDto = MobileNumberDtoFactory(
                sessionManager = sessionManager
            ).create(
                mobileNumber = addMobileNumberUIState.value.mobileNumber.toLong()
            )

            val validationResult = requestValidator.validate(
                authId = sessionManager.authId.first(),
                householdId = sessionManager.householdId.first(),
                mobileNumber = addMobileNumberUIState.value.mobileNumber
            )

            //user understandable errors should be placed in a class
            if (validationResult is Result.Failure) {

                addMobileNumberUIState.update {
                    it.copy(
                        apiResponse = "failure: ${requestErrorMapper.map(validationResult.error)}",
                        isLoading = false,
                        shouldProceed = false
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
                            apiResponse = "mobile number has been added",
                            isLoading = false,
                            shouldProceed = true
                        )
                    }
                }

                is Result.Failure -> {

                    addMobileNumberUIState.update {
                        it.copy(
                            apiResponse = "failure: ${responseErrorMapper.map(result.error)}",
                            shouldProceed = false,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun onMobileNumberChanged(mobileNumber: String) {
        addMobileNumberUIState.update {
            it.copy(
                mobileNumber = mobileNumber
            )
        }
    }

    fun resetMobileNumberError() {
        addMobileNumberUIState.update {
            it.copy(
                mobileNumberError = ""
            )
        }
    }
}