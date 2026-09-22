package com.raza.householdrecharge.data.remote.dto

data class InvitationDto(
    var id: String = "",
    val code: String = "",
    val householdId: String = "",
    val createdBy: String = "",
    val createdAt: String = "",
    val expiresAt: String = "",
    val status: String = "",
    val usedBy: String = "",
    val usedAt: Long = 0L
)