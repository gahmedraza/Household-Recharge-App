package com.raza.householdrecharge.presentation.recharge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.logging.log
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.data.repository.RechargeRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.RechargeDto
import com.raza.householdrecharge.data.remote.mapper.RechargeDtoMapper
import com.raza.householdrecharge.domain.validator.RechargeValidator
import com.raza.householdrecharge.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class RechargeListingViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val rechargeRepository: RechargeRepository,
    private val validator: RechargeValidator
) : BaseViewModel() {

    var rechargeList2 = MutableStateFlow<List<RechargeDto>>(emptyList())

    init {
        log("recharge listing viewmodel init called")
        observeRecharges()
    }

    fun observeRecharges() {
        viewModelScope.launch {
            rechargeRepository.observeRecharges().collect { rechargeDtoList ->
                rechargeList2.value = rechargeDtoList.map { rechargeDto ->
                    RechargeDtoMapper.map(rechargeDto)
                }
            }
        }
    }

    fun loadAllRecharges(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
        memberId: String,
        mobileNumber: String
    ) {
        viewModelScope.launch {
            val validationResult = validator.validateRechargeListingApiCall(
                userId = sessionManager.authId.first(),
                householdId = sessionManager.householdId.first(),
                memberId = memberId,
                mobileNumber = mobileNumber
            )

            //user understandable errors should be placed in a class
            if(validationResult is Result.Failure) {
                onFailure("failure")
                return@launch
            }

            rechargeRepository.getAllRecharges()
        }
    }
}