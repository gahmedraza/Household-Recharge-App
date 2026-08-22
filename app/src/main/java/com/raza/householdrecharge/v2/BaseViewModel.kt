package com.raza.householdrecharge.v2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    var mobileNumber by mutableStateOf("")
    var password by mutableStateOf("")
}