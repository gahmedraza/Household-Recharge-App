package com.raza.householdrecharge.presentation.dashbord

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.logging.log
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto
import com.raza.householdrecharge.data.remote.mapper.MobileNumberDtoMapper
import com.raza.householdrecharge.data.repository.MobileNumberRepository
import com.raza.householdrecharge.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val mobileNumberRepository: MobileNumberRepository

) : BaseViewModel() {
    val mobileNumberList = MutableStateFlow<List<MobileNumberDto>>(emptyList())

    init {
        log("dashboard viewmodel init called...")
        observeMobileNumbers()
    }

    fun getAllMobileNumbers(
    ) {
        viewModelScope.launch {
            mobileNumberRepository.getAllMobileNumbers()
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
}