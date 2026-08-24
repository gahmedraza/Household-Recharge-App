package com.raza.householdrecharge.v2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    var mobileNumber by mutableStateOf("")

    var password by mutableStateOf("")

    val householdId = "11001"

    val memberId = "1011"

    var userId by mutableStateOf("")

    var name by mutableStateOf("")

    var lastRechargeDate by mutableStateOf("")

    var planAmount by mutableStateOf("")

    var planExpiryDate by mutableStateOf("")

    var planDurationDays by mutableStateOf("")

    var daysToExpiry by mutableStateOf("")

    var rechargeRequested by mutableStateOf("")

    var household by mutableStateOf<Household>(Household())

    var isLoading by mutableStateOf(false)

}
