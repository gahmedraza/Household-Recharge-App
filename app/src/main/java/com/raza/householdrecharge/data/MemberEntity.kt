package com.raza.householdrecharge.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "members")
data class MemberEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val householdId: Long = 1,
    val name: String,
    val mobileNumber: String,
    val planDurationDays: Int,
    val lastRechargeDate: Long?,
    val planExpiryDate: Long?,
    val rechargeRequested: Boolean = false
)