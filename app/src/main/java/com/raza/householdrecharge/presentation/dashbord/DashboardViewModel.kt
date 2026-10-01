package com.raza.householdrecharge.presentation.dashbord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.MobileNumberUseCase
import com.raza.householdrecharge.domain.usecase.RechargeUseCase
import com.raza.householdrecharge.domain.validator.request.MobileNumberRequestValidator
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
class DashboardViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val mobileNumberUseCase: MobileNumberUseCase,
    private val rechargeUseCase: RechargeUseCase,
    private val rechargeRequestValidator: RechargeRequestValidator,
    private val mobileNumberRequestValidator: MobileNumberRequestValidator,
    private val requestErrorMapper: RequestErrorMapper
) : ViewModel() {

    var dashboardUIState = MutableStateFlow(DashboardUIState())

    init {
        Logger.log("dashboard viewmodel init called...")
        observeMobileNumbers()
        observeRecharges()
    }

    fun getAllMobileNumbers(
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val validationResult = mobileNumberRequestValidator.validate(
                authId = sessionManager.authId.first(),
                householdId = sessionManager.householdId.first(),
            )

            if(validationResult is Result.Failure) {

                dashboardUIState.update {
                    it.copy(
                        apiResponse = requestErrorMapper.map(validationResult.error),
                        showBottomSheet = true
                    )
                }

                return@launch
            }

            mobileNumberUseCase.getAllMobileNumbers(
                householdId = sessionManager.householdId.first()
            )
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

                dashboardUIState.update {
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

    fun observeMobileNumbers(
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            mobileNumberUseCase.observeMobileNumbers().collect { mobileNumberList ->

                dashboardUIState.update {
                    it.copy(
                        mobileNumberList = mobileNumberList
                    )
                }
            }
        }
    }

    fun observeRecharges(
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            rechargeUseCase.observeRecharges().collect { rechargeList ->

                dashboardUIState.update {
                    it.copy(
                        rechargeList = rechargeList
                    )
                }
            }
        }
    }

    fun onShowBottomSheetModified(showBottomSheet: Boolean) {
        dashboardUIState.update {
            it.copy(
                showBottomSheet = showBottomSheet
            )
        }
    }
}