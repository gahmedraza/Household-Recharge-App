package com.raza.householdrecharge.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("households")
data class HouseholdEntity(
    @PrimaryKey
    var householdId: String,
    val householdName: String,
    val accountId: String
)