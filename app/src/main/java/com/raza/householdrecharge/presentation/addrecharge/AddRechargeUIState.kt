package com.raza.householdrecharge.presentation.addrecharge

data class AddRechargeUIState(
    var isLoading: Boolean = false,
    var planExpiryDate: String = "",
    var rechargeDescription: String = "",
    var amount: String = "",
    var date: String = "",
    var rechargedBy: String = "",
    var planExpiryDateError: String = "",
    var rechargeDescriptionError: String = "",
    var amountError: String = "",
    var dateError: String = "",
    var rechargedByError: String = "",
    var apiResponse: String = "",
    var showBottomSheet: Boolean = false
)