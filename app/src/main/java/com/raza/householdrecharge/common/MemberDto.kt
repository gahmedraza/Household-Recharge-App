package com.raza.householdrecharge.common

data class MemberDto(
    val name: String = "",
    val mobileNumber: String = "",
    val planDurationDays: String = "",
    val lastRechargeDate: String? = "",
    val planExpiryDate: String? = "",
    val planAmount: String? = ""
)