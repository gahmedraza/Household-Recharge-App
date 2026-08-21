package com.raza.householdrecharge.v2.splash

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.raza.householdrecharge.ui.theme.HouseholdRechargeTheme

class HandlerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HouseholdRechargeTheme(dynamicColor = false) {
                SplashScreen(
                    onSplashFinished = {
                        Log.d("TAG", "Splash finished")
                    }
                )
            }
        }
    }
}