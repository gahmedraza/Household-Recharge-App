package com.raza.householdrecharge.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("mobile_numbers")
data class MobileNumberEntity(
    @PrimaryKey
    val id: Long,
    var mobileNumber: Long = 0,
    var accountId: String = "",
    var householdId: String = ""
)