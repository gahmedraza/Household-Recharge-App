package com.raza.householdrecharge.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("recharges")
data class RechargeEntity(
    @PrimaryKey
    val id: String = "",
    val rechargeAmount: Int = 0,
    val rechargeDate: Long = 0L,
    val expiryDate: Long = 0L,
    val rechargedBy: String = "",
    val rechargeDescription: String = "",
    //data modeling details
    val mobileNumber: Long = 0,
    val accountId: String = "",
    val householdId: String = ""
)