package com.raza.householdrecharge.v2.dashbord

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.raza.householdrecharge.v2.BaseViewModel

class DashboardViewModel : BaseViewModel() {
    val items by mutableStateOf<List<DashboardItem>>(emptyList())
}