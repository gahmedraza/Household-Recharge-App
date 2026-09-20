package com.raza.householdrecharge.presentation.recharge

import com.raza.householdrecharge.domain.model.Recharge

data class RechargeListingUIState(
    var rechargeList: List<Recharge> = emptyList()
)