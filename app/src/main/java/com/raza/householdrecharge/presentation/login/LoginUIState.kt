package com.raza.householdrecharge.presentation.login

data class LoginUIState(
    var mobileNumber: String = "",
    var password: String = "",
    var mobileNumberError: String = "",
    var passwordError: String = "",
    var isLoading: Boolean = false,
    var apiResponse: String = "",
    var showBottomSheet: Boolean = false
)