package com.raza.householdrecharge.v2.data.dto

data class InvitationDto(
    val code: String = "",
    val householdId: String = "",
    val createdBy: String = "",
    val createdAt: String = "",
    val expiresAt: String = "",
    val status: String = ""
)