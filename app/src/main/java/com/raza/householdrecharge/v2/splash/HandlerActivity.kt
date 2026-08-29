package com.raza.householdrecharge.v2.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.raza.householdrecharge.v1.ui.theme.HouseholdRechargeTheme
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