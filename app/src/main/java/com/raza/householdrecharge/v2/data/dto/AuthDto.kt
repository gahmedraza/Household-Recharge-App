package com.raza.householdrecharge.v2.data.dto

data class AuthDto(
    val accountName: String? = null,
    val mobileNumber: String,
    val password: String
)