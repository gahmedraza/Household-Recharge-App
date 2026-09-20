package com.raza.householdrecharge.domain.model

data class Invitation(
    val code: String = "",
    val householdId: String = "",
    val createdBy: String = "",
    val createdAt: String = "",
    val expiresAt: String = "",
    val status: String = "",
    val usedBy: String = "",
    val usedAt: Long = 0L
)