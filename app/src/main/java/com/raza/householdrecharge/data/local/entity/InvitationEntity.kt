package com.raza.householdrecharge.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

//todo the code should not be primary key
//todo collection row id should be the primary key
@Entity("invitations")
data class InvitationEntity(
    @PrimaryKey
    val code: String,
    val householdId: String,
    val createdBy: String,
    val createdAt: String,
    val expiresAt: String,
    val status: String,
    val usedBy: String,
    val usedAt: Long
)