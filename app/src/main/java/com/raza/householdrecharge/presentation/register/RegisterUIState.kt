package com.raza.householdrecharge.presentation.register

data class RegisterUIState(
    var isLoading: Boolean = false,
    var accountName: String = "",
    var mobileNumber: String = "",
    var password: String = "",
    var accountNameError: String = "",
    var mobileNumberError: String = "",
    var passwordError: String = "",
    var authId: String = ""
)