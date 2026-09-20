package com.raza.householdrecharge.presentation.dashbord

import com.raza.householdrecharge.domain.model.MobileNumber
import com.raza.householdrecharge.domain.model.Recharge

data class DashboardUIState(
    var isLoading: Boolean = false,
    var mobileNumberList: List<MobileNumber> = emptyList(),
    var rechargeList: List<Recharge> = emptyList()
)