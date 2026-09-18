package com.raza.householdrecharge.presentation.dashbord

import com.raza.householdrecharge.data.remote.dto.MobileNumberDto
import com.raza.householdrecharge.data.remote.dto.RechargeDto

data class DashboardUIState(
    var isLoading: Boolean = false,
    var mobileNumberList: List<MobileNumberDto> = emptyList(),
    var rechargeList: List<RechargeDto> = emptyList()
)