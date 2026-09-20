package com.raza.householdrecharge.domain.model

data class MobileNumber(
    var id: String = "",
    var mobileNumber: Long = 0,
    var lastRechargeId: String = "",
    var accountId: String = "",
    var householdId: String = ""
)