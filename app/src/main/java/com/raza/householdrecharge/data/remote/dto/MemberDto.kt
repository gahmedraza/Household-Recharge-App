package com.raza.householdrecharge.data.remote.dto

data class MemberDto(
    val name: String = "",
    val mobileNumber: String = "",
    val planDurationDays: String = "",
    val lastRechargeDate: String? = "",
    val planExpiryDate: String? = "",
    val planAmount: String? = ""
)