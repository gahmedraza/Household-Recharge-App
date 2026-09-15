package com.raza.householdrecharge.presentation.dashbord

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.logging.log
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto
import com.raza.householdrecharge.data.remote.dto.RechargeDto
import com.raza.householdrecharge.data.remote.mapper.MobileNumberDtoMapper
import com.raza.householdrecharge.data.remote.mapper.RechargeDtoMapper
import com.raza.householdrecharge.data.repository.MobileNumberRepository
import com.raza.householdrecharge.data.repository.RechargeRepository
import com.raza.householdrecharge.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val mobileNumberRepository: MobileNumberRepository,
    private val rechargeRepository: RechargeRepository
) : BaseViewModel() {
    val mobileNumberList = MutableStateFlow<List<MobileNumberDto>>(emptyList())
    var rechargeList = MutableStateFlow<List<RechargeDto>>(emptyList())

    init {
        log("dashboard viewmodel init called...")
        observeMobileNumbers()
        observeRecharges()
    }

    fun getAllMobileNumbers(
    ) {
        viewModelScope.launch {
            mobileNumberRepository.getAllMobileNumbers()
        }
    }

    fun getAllRecharges(
    ) {
        viewModelScope.launch {
            rechargeRepository.getAllRecharges()
        }
    }

    fun observeMobileNumbers(
    ) {
        viewModelScope.launch {
            mobileNumberRepository.observeMobileNumbers().collect { mobileNumberEntityList ->

                val mobileNumberDtoList = MobileNumberDtoMapper.map(mobileNumberEntityList)

                mobileNumberList.value = mobileNumberDtoList
            }
        }
    }

    fun observeRecharges(
    ) {
        viewModelScope.launch {
            rechargeRepository.observeRecharges().collect { rechargeDtoList ->

                val rechargeDtoList = RechargeDtoMapper.map(rechargeDtoList)

                rechargeList.value = rechargeDtoList
            }
        }
    }
}