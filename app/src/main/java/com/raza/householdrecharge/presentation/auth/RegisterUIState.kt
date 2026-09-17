package com.raza.householdrecharge.presentation.auth

data class RegisterUIState(
    var isLoading: Boolean = false,
    var accountName: String = "",
    var mobileNumber: String = "",
    var password: String = "",
    var authId: String = ""
)