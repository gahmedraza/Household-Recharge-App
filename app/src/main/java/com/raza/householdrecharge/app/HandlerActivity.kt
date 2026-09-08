package com.raza.householdrecharge.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.raza.householdrecharge.app.navigation.OnboardingNavigation
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme

class HandlerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HouseholdRechargeTheme(dynamicColor = false) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    OnboardingNavigation()
                }
            }
        }
    }
}