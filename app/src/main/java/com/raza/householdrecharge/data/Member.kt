package com.raza.householdrecharge.data

data class Member(
    val id: String = "",
    val name: String = "",
    val mobileNumber: String = "",
    val planDurationDays: Int = 0,
    val lastRechargeDate: Long? = 0L,
    val planExpiryDate: Long? = 0L,
    val rechargeRequested: Boolean = false,
    val planAmount: Int = 0
)
