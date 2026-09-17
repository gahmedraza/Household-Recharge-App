package com.raza.householdrecharge.domain.model

data class FindHouseholdResponse(
    val householdId: String = "",
    val householdName: String = "",
    val invitationCode: String = ""
)