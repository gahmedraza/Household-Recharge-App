package com.raza.householdrecharge.presentation.addmobilenumber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.factory.MobileNumberDtoFactory
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.MobileNumberUseCase
import com.raza.householdrecharge.domain.validator.MobileNumberRequestValidator
import com.raza.householdrecharge.presentation.common.MobileNumberValidator
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
    private val mobileNumberValidator: MobileNumberValidator
) : ViewModel() {

    var addMobileNumberUIState = MutableStateFlow(AddMobileNumberUIState())

    fun addMobileNumber(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            //
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
            //

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
                        apiResponse = validationResult.error.toString(),
                        showBottomSheet = true
                    )
                }

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
                            apiResponse = "successfully parsed the response",
                            showBottomSheet = true
                        )
                    }

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
                            apiResponse = result.error,
                            showBottomSheet = true
                        )
                    }

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

    fun onShowBottomSheetModified(showBottomSheet: Boolean) {
        addMobileNumberUIState.update {
            it.copy(
                showBottomSheet = showBottomSheet
            )
        }
    }
}