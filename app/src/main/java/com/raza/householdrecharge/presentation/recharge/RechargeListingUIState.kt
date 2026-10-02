package com.raza.householdrecharge.presentation.recharge

import com.raza.householdrecharge.domain.model.Recharge

data class RechargeListingUIState(
    var isLoading: Boolean = false,
    var rechargeList: List<Recharge> = emptyList(),
    var apiResponse: String = "",
    var shouldProceed: Boolean = false,
    var showBottomSheet: Boolean = false
)