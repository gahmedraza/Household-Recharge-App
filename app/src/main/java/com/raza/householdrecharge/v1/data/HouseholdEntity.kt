package com.raza.householdrecharge.v1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "households")
data class HouseholdEntity(
    @PrimaryKey val id: Long = 1,
    val name: String
)
