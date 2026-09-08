package com.raza.householdrecharge.presentation.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.raza.householdrecharge.domain.model.Household

open class BaseViewModel : ViewModel() {
    var mobileNumber by mutableStateOf("")

    var accountName by mutableStateOf("")

    var lastRechargeDate by mutableStateOf("")

    var planAmount by mutableStateOf("")

    var planExpiryDate by mutableStateOf("")

    var planDurationDays by mutableStateOf("")

    var daysToExpiry by mutableStateOf("")

    var household by mutableStateOf<Household>(Household())

    var isLoading by mutableStateOf(false)

}