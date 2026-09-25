package com.raza.householdrecharge.presentation.addrecharge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.factory.RechargeDtoFactory
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.RechargeUseCase
import com.raza.householdrecharge.domain.validator.request.RechargeRequestValidator
import com.raza.householdrecharge.presentation.common.ExpiryDateValidator
import com.raza.householdrecharge.presentation.common.RechargeAmountValidator
import com.raza.householdrecharge.presentation.common.RechargeDateValidator
import com.raza.householdrecharge.presentation.common.RechargeDescriptionValidator
import com.raza.householdrecharge.presentation.common.RechargedByValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRechargeViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val rechargeUseCase: RechargeUseCase,
    private val requestValidator: RechargeRequestValidator,
    private val rechargeAmountValidator: RechargeAmountValidator,
    private val rechargeDateValidator: RechargeDateValidator,
    private val expiryDateValidator: ExpiryDateValidator,
    private val rechargedByValidator: RechargedByValidator,
    private val rechargeDescriptionValidator: RechargeDescriptionValidator
) : ViewModel() {

    var addRechargeUIState = MutableStateFlow(AddRechargeUIState())

    //todo remove mobile number from params
    fun addRecharge(
        mobileNumber: String,
        mobileNumberId: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            //
            //validate the input fields
            addRechargeUIState.update {
                it.copy(
                    amountError = rechargeAmountValidator.validate(
                        addRechargeUIState.value.amount,
                        addRechargeUIState.value.amountError
                    )
                )
            }

            addRechargeUIState.update {
                it.copy(
                    dateError = rechargeDateValidator.validate(
                        addRechargeUIState.value.date,
                        addRechargeUIState.value.dateError
                    )
                )
            }

            addRechargeUIState.update {
                it.copy(
                    planExpiryDateError = expiryDateValidator.validate(
                        addRechargeUIState.value.planExpiryDate,
                        addRechargeUIState.value.planExpiryDateError
                    )
                )
            }

            addRechargeUIState.update {
                it.copy(
                    rechargedByError = rechargedByValidator.validate(
                        addRechargeUIState.value.rechargedBy,
                        addRechargeUIState.value.rechargedByError
                    )
                )
            }

            addRechargeUIState.update {
                it.copy(
                    rechargeDescriptionError = rechargeDescriptionValidator.validate(
                        addRechargeUIState.value.rechargeDescription,
                        addRechargeUIState.value.rechargeDescriptionError
                    )
                )
            }

            if(
                addRechargeUIState.value.amountError.isNotEmpty() ||
                addRechargeUIState.value.dateError.isNotEmpty() ||
                addRechargeUIState.value.planExpiryDateError.isNotEmpty() ||
                addRechargeUIState.value.rechargedByError.isNotEmpty() ||
                addRechargeUIState.value.rechargeDescriptionError.isNotEmpty()
            ) {

                return@launch
            }
            //

            addRechargeUIState.update {
                it.copy(
                    isLoading = true
                )
            }

            val rechargeDto = RechargeDtoFactory(
                sessionManager = sessionManager
            ).create(
                rechargeAmount = addRechargeUIState.value.amount.toInt(),
                rechargeDate = addRechargeUIState.value.date.toLong(),
                expiryDate = addRechargeUIState.value.planExpiryDate.toLong(),
                rechargedBy = addRechargeUIState.value.rechargedBy,
                rechargeDescription = addRechargeUIState.value.rechargeDescription,
                mobileNumber = mobileNumber.toLong()
            )

            val validationResult = requestValidator.validateAddRechargeApiCall(
                authId = sessionManager.authId.first(),
                householdId = sessionManager.householdId.first(),
                mobileNumber = mobileNumber,
                mobileNumberId = mobileNumberId
            )

            //user understandable errors should be placed in a class
            if(validationResult is Result.Failure) {
                addRechargeUIState.update {
                    it.copy(
                        apiResponse = validationResult.error.toString(),
                        showBottomSheet = true
                    )
                }

                addRechargeUIState.update {
                    it.copy(
                        isLoading = false
                    )
                }

                onFailure("failure")
                return@launch
            }

            val result = rechargeUseCase.addRechargeAndUpdateMobileNumber(
                rechargeDto = rechargeDto,
                mobileNumberId = mobileNumberId
            )

            when (result) {

                is Result.Success<String> -> {
                    addRechargeUIState.update {
                        it.copy(
                            apiResponse = "successfully parsed the response",
                            showBottomSheet = true
                        )
                    }

                    addRechargeUIState.update {
                        it.copy(
                            isLoading = false
                        )
                    }

                    onSuccess()
                }

                is Result.Failure<String> -> {
                    addRechargeUIState.update {
                        it.copy(
                            apiResponse = result.error,
                            showBottomSheet = true
                        )
                    }

                    addRechargeUIState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    onFailure(result.error)
                }
            }
        }
    }

    fun onPlanExpiryDateChanged(planExpiryDate: String) {
        addRechargeUIState.update {
            it.copy(
                planExpiryDate = planExpiryDate
            )
        }
    }

    fun resetPlanExpiryDateError() {
        addRechargeUIState.update {
            it.copy(
                planExpiryDateError = ""
            )
        }
    }

    fun onRechargeDescriptionChanged(rechargeDescription: String) {
        addRechargeUIState.update {
            it.copy(
                rechargeDescription = rechargeDescription
            )
        }
    }

    fun resetRechargeDescriptionError() {
        addRechargeUIState.update {
            it.copy(
                rechargeDescriptionError = ""
            )
        }
    }

    fun onAmountChanged(amount: String) {
        addRechargeUIState.update {
            it.copy(
                amount = amount
            )
        }
    }

    fun resetAmountError() {
        addRechargeUIState.update {
            it.copy(
                amountError = ""
            )
        }
    }

    fun onDateChanged(date: String) {
        addRechargeUIState.update {
            it.copy(
                date = date
            )
        }
    }

    fun resetDateError() {
        addRechargeUIState.update {
            it.copy(
                dateError = ""
            )
        }
    }

    fun onRechargedByChanged(rechargedBy: String) {
        addRechargeUIState.update {
            it.copy(
                rechargedBy = rechargedBy
            )
        }
    }

    fun resetRechargedByError() {
        addRechargeUIState.update {
            it.copy(
                rechargedByError = ""
            )
        }
    }

    fun onShowBottomSheetModified(showBottomSheet: Boolean) {
        addRechargeUIState.update {
            it.copy(
                showBottomSheet = showBottomSheet
            )
        }
    }
}