package com.raza.householdrecharge.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("users")
data class UserEntity(
    @PrimaryKey
    val userId: String,
    val email: String,
    val password: String,
    val photoUrl: String = ""
)