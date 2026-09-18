package com.raza.householdrecharge.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("accounts")
data class AccountEntity(
    @PrimaryKey
    val accountId: String,
    val accountName: String,
    val householdId: String
)