package com.raza.householdrecharge.presentation.recharge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.RechargeUseCase
import com.raza.householdrecharge.domain.validator.request.RechargeRequestValidator
import com.raza.householdrecharge.presentation.error.RequestErrorMapper
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
    private val rechargeRequestValidator: RechargeRequestValidator,
    private val requestErrorMapper: RequestErrorMapper
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
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val validationResult = rechargeRequestValidator.validate(
                authId = sessionManager.authId.first(),
                householdId = sessionManager.householdId.first(),
            )

            if(validationResult is Result.Failure) {

                rechargeListingUIState.update {
                    it.copy(
                        apiResponse = requestErrorMapper.map(validationResult.error),
                        showBottomSheet = true
                    )
                }

                return@launch
            }

            rechargeUseCase.getAllRecharges(
                householdId = sessionManager.householdId.first()
            )

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