package com.raza.householdrecharge.data.remote.dto

data class InvitationDto(
    val code: String = "",
    val householdId: String = "",
    val createdBy: String = "",
    val createdAt: String = "",
    val expiresAt: String = "",
    val status: String = ""
)