package com.raza.householdrecharge.ui.addrecharge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.common.BaseViewModel
import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.factory.RechargeDtoFactory
import com.raza.householdrecharge.data.repository.RechargeRepository
import com.raza.householdrecharge.domain.validator.RechargeValidator
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddRechargeViewModel(
    private val sessionManager: SessionManager,
    private val rechargeRepository: RechargeRepository,
    private val validator: RechargeValidator
) : BaseViewModel() {

    var rechargeDescription by mutableStateOf("")
    var amount by mutableStateOf("")
    var date by mutableStateOf("")
    var rechargedBy by mutableStateOf("")

    fun addRecharge(
        mobileNumber: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            //
            isLoading = true

            val rechargeDto = RechargeDtoFactory(
                sessionManager = sessionManager
            ).create(
                rechargeAmount = amount.toInt(),
                rechargeDate = date.toLong(),
                expiryDate = planExpiryDate.toLong(),
                rechargedBy = rechargedBy,
                rechargeDescription = rechargeDescription,
                mobileNumber = mobileNumber.toLong()
            )

            val validationResult = validator.validateAddRechargeApiCall(
                userId = sessionManager.authId.first(),
                householdId = sessionManager.householdId.first(),
                mobileNumber = mobileNumber
            )

            //user understandable errors should be placed in a class
            if(validationResult is Result.Failure) {
                isLoading = false
                onFailure("failure")
                return@launch
            }
            //

            val result = rechargeRepository.addRecharge(
                rechargeDto = rechargeDto
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