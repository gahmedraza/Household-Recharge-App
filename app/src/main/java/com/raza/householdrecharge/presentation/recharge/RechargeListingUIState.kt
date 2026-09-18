package com.raza.householdrecharge.presentation.recharge

import com.raza.householdrecharge.data.remote.dto.RechargeDto

data class RechargeListingUIState(
    var rechargeList: List<RechargeDto> = emptyList()
)