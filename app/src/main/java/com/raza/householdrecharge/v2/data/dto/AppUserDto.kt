package com.raza.householdrecharge.v2.data.dto

data class AppUserDto(
    val authId: String,
    val accountId: String = "",
    val householdId: String,
    val memberId: String = "",
    val mobileNumber: String = ""
)