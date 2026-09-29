package com.raza.householdrecharge.presentation.setuphousehold

import com.raza.householdrecharge.domain.model.FindHouseholdResponse

data class HouseholdUIState(
    var isLoading: Boolean = false,
    var invitationCode: String = "",
    var invitationCodeError: String = "",
    var householdName: String = "",
    var householdNameError: String = "",
    var apiResponse: String = "",
    var shouldProceed: Boolean = false,
    var findHouseholdResponse: FindHouseholdResponse? = null
)