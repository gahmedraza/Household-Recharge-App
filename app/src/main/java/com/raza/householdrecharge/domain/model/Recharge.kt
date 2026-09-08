package com.raza.householdrecharge.domain.model

data class Recharge(
    val id: Long = 0,
    val memberId: Long = 0,
    val amount: String? = null,
    val date: String? = null,
    val rechargedBy: String? = null
)