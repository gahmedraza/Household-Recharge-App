package com.raza.householdrecharge.v2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    var mobileNumber by mutableStateOf("")

    var password by mutableStateOf("")

    val householdId = "11001"

    val memberId = "1011"

    var name by mutableStateOf("")

    var lastRechargeDate by mutableLongStateOf(0)

    var planAmount by mutableStateOf("")

    var planExpiryDate by mutableLongStateOf(0)

    var planDurationDays by mutableIntStateOf(0)

    var daysToExpiry by mutableStateOf("")

    var rechargeRequested by mutableStateOf("")

    var household by mutableStateOf<Household>(Household())
}
