package com.raza.householdrecharge.v2.common

data class MemberDto(
    val id: Long = 0L,
    val name: String = "",
    val mobileNumber: String = "",
    val planDurationDays: String = "",
    val lastRechargeDate: String? = "",
    val planExpiryDate: String? = "",
    val planAmount: String? = ""
)