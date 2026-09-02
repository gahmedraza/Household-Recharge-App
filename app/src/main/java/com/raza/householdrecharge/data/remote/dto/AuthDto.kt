package com.raza.householdrecharge.data.remote.dto

data class AuthDto(
    val accountName: String? = null,
    val mobileNumber: String,
    val password: String
)