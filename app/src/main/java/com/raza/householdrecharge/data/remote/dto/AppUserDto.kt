package com.raza.householdrecharge.data.remote.dto

data class AppUserDto(
    val authId: String,
    val accountId: String = "",
    val householdId: String,
    val memberId: String = "",
    val mobileNumber: String = ""
)