package com.raza.householdrecharge.presentation.setuphousehold

data class HouseholdUIState(
    var isLoading: Boolean = false,
    var invitationCode: String = "",
    var invitationCodeError: String = "",
    var householdName: String = "",
    var householdNameError: String = ""
)