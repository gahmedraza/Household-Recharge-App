package com.raza.householdrecharge.domain.model

data class Recharge(
    val id: String = "",
    val rechargeAmount: Int = 0,
    val rechargeDate: Long = 0,
    val expiryDate: Long = 0,
    val rechargedBy: String = "",
    val rechargeDescription: String = "",
    val mobileNumber: Long = 0L,
    val accountId: String = "",
    val householdId: String = ""
)