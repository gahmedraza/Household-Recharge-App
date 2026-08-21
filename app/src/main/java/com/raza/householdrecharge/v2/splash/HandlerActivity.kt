package com.raza.householdrecharge.v2.splash

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavGraph
import com.raza.householdrecharge.ui.theme.HouseholdRechargeTheme
import com.raza.householdrecharge.v2.navigation.V2Navigation

class HandlerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HouseholdRechargeTheme(dynamicColor = false) {
                V2Navigation()
            }
        }
    }
}