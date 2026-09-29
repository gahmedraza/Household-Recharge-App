package com.raza.householdrecharge.presentation.account

data class AccountUIState(
    var isLoading: Boolean = false,
    var profileName: String = "",
    var profileHousehold: String = "",
    var apiStatus: String = "",
    var shouldProceed: Boolean = false
)