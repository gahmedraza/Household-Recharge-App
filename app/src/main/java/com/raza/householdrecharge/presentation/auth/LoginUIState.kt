package com.raza.householdrecharge.presentation.auth

data class LoginUIState(
    var mobileNumber: String = "",
    var password: String = "",
    var mobileNumberError: String = "",
    var passwordError: String = "",
    var isLoading: Boolean = false
)