package com.raza.householdrecharge.data.remote.dto

import com.raza.householdrecharge.domain.model.Account

class AccountEligibilityDto(
    val isEligible: Boolean,
    val account: Account
)