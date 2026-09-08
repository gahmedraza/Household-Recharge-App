package com.raza.householdrecharge.ui.recharge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.common.BaseViewModel
import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.data.repository.RechargeRepository
import com.raza.householdrecharge.domain.model.Recharge
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.RechargeDto
import com.raza.householdrecharge.domain.validator.RechargeValidator

class RechargeListingViewModel(
    private val sessionManager: SessionManager,
    private val rechargeRepository: RechargeRepository,
    private val validator: RechargeValidator
) : BaseViewModel() {

    var rechargeList by mutableStateOf<List<RechargeDto>>(emptyList())

    fun loadAllRecharges(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
        memberId: String,
        mobileNumber: String
    ) {
        viewModelScope.launch {
            isLoading = true

            val validationResult = validator.validateRechargeListingApiCall(
                userId = sessionManager.authId.first(),
                householdId = sessionManager.householdId.first(),
                memberId = memberId,
                mobileNumber = mobileNumber
            )

            //user understandable errors should be placed in a class
            if(validationResult is Result.Failure) {
                isLoading = false
                onFailure("failure")
                return@launch
            }

            val result = rechargeRepository.getAllRecharges()

            when(result) {

                is Result.Success<List<RechargeDto>> -> {
                    isLoading = false
                    rechargeList = result.data
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