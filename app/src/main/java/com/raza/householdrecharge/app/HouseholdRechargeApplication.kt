package com.raza.householdrecharge.app

import android.app.Application
import com.raza.householdrecharge.data.session.SessionManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HouseholdRechargeApplication : Application() {
    //todo this can be non app repo object
    val sessionManager by lazy {
        SessionManager(this)
    }
}