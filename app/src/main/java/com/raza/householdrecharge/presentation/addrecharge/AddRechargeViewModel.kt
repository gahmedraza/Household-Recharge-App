package com.raza.householdrecharge.presentation.addrecharge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.factory.RechargeDtoFactory
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.RechargeUseCase
import com.raza.householdrecharge.domain.validator.RechargeRequestValidator
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
    private val requestValidator: RechargeRequestValidator
) : ViewModel() {

    var addRechargeUIState = MutableStateFlow(AddRechargeUIState())

    fun addRecharge(
        mobileNumber: String,
        mobileNumberId: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
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
                userId = sessionManager.authId.first(),
                householdId = sessionManager.householdId.first(),
                mobileNumber = mobileNumber
            )

            //user understandable errors should be placed in a class
            if(validationResult is Result.Failure) {
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
                            isLoading = false
                        )
                    }
                    onSuccess()
                }

                is Result.Failure<String> -> {
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

    fun onRechargeDescriptionChanged(rechargeDescription: String) {
        addRechargeUIState.update {
            it.copy(
                rechargeDescription = rechargeDescription
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

    fun onDateChanged(date: String) {
        addRechargeUIState.update {
            it.copy(
                date = date
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
}