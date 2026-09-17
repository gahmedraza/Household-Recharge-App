package com.raza.householdrecharge.presentation.setuphousehold

import com.raza.householdrecharge.domain.model.Household

data class HouseholdUIState(
    var isLoading: Boolean = false,
    var invitationCode: String = "",
    var household: Household = Household()
)