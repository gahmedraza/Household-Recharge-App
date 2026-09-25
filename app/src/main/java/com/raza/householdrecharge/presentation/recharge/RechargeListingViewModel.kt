package com.raza.householdrecharge.presentation.recharge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.RechargeUseCase
import com.raza.householdrecharge.domain.validator.request.RechargeRequestValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RechargeListingViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val rechargeUseCase: RechargeUseCase,
    private val validator: RechargeRequestValidator
) : ViewModel() {

    var rechargeListingUIState = MutableStateFlow(RechargeListingUIState())

    init {
        Logger.log("recharge listing viewmodel init called")
        observeRecharges()
    }

    fun observeRecharges() {
        viewModelScope.launch(Dispatchers.IO) {
            rechargeUseCase.observeRecharges().collect { rechargeList ->
                rechargeListingUIState.update {
                    it.copy(
                        rechargeList = rechargeList
                    )
                }
            }
        }
    }

    fun getAllRecharges(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
        memberId: String,
        mobileNumber: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val validationResult = validator.validateRechargeListingApiCall(
                authId = sessionManager.authId.first(),
                householdId = sessionManager.householdId.first(),
                memberId = memberId,
                mobileNumber = mobileNumber
            )

            //user understandable errors should be placed in a class
            if(validationResult is Result.Failure) {
                onFailure("failure")

                rechargeListingUIState.update {
                    it.copy(
                        apiResponse = validationResult.error.toString(),
                        showBottomSheet = true
                    )
                }

                return@launch
            }

            rechargeUseCase.getAllRecharges()

        }

        viewModelScope.launch(Dispatchers.IO) {
            rechargeUseCase.queryDatabase()
        }
    }

    fun onShowBottomSheetModified(showBottomSheet: Boolean) {
        rechargeListingUIState.update {
            it.copy(
                showBottomSheet = showBottomSheet
            )
        }
    }
}