package com.raza.householdrecharge.v2.splash

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import com.raza.householdrecharge.ui.theme.HouseholdRechargeTheme
import com.raza.householdrecharge.v2.navigation.V2Navigation

class HandlerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HouseholdRechargeTheme(dynamicColor = false) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    V2Navigation()
                }
            }
        }
    }
}