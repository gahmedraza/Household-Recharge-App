package com.raza.householdrecharge.v1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recharge_requests")
data class RechargeRequestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val membersId: Long,
    val requestedAt: Long,
    val completedAt: Long? = null,
    val status: RechargeRequestStatus = RechargeRequestStatus.PENDING
)