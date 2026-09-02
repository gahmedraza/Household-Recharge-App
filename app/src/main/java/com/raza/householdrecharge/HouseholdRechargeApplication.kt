package com.raza.householdrecharge

import android.app.Application
import com.raza.householdrecharge.common.SessionManager

class HouseholdRechargeApplication : Application() {
    val sessionManager by lazy {
        SessionManager(this)
    }
}