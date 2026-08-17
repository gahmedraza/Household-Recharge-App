package com.raza.householdrecharge.data

data class Member(
    val id: Long,
    val name: String,
    val mobileNumber: String,
    val planDurationDays: Int,
    val lastRechargeDate: Long?,
    val planExpiryDate: Long?,
    val rechargeRequested: Boolean = false
)

