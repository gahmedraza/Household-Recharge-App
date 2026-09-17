package com.raza.householdrecharge.presentation.dashbord

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto
import com.raza.householdrecharge.data.remote.dto.RechargeDto
import com.raza.householdrecharge.data.remote.mapper.MobileNumberDtoMapper
import com.raza.householdrecharge.data.remote.mapper.RechargeDtoMapper
import com.raza.householdrecharge.data.repository.MobileNumberRepository
import com.raza.householdrecharge.domain.usecase.RechargeUseCase
import com.raza.householdrecharge.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val mobileNumberRepository: MobileNumberRepository,
    private val rechargeUseCase: RechargeUseCase
) : ViewModel() {

    var dashboardUIState by mutableStateOf(DashboardUIState())
    val mobileNumberList = MutableStateFlow<List<MobileNumberDto>>(emptyList())
    var rechargeList = MutableStateFlow<List<RechargeDto>>(emptyList())

    init {
        Logger.log("dashboard viewmodel init called...")
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
            rechargeUseCase.getAllRecharges()
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
            rechargeUseCase.observeRecharges().collect { rechargeDtoList ->

                val rechargeDtoList = RechargeDtoMapper.map(rechargeDtoList)

                rechargeList.value = rechargeDtoList
            }
        }
    }
}