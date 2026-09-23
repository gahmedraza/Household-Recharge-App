package com.raza.householdrecharge.presentation.addmobilenumber

data class AddMobileNumberUIState(
    var isLoading: Boolean = false,
    var mobileNumber: String = "",
    var mobileNumberError: String = "",
    var apiResponse: String = "",
    var showBottomSheet: Boolean = false
)