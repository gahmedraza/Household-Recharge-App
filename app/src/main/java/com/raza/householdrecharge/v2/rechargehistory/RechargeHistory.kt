package com.raza.householdrecharge.v2.rechargehistory

data class RechargeHistory(
    val id: Long = 0,
    val memberId: Long = 0,
    val amount: String?,
    val date: String?,
    val rechargedBy: String?
)