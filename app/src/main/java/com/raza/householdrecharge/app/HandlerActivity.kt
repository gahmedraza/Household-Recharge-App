package com.raza.householdrecharge.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.raza.householdrecharge.app.navigation.OnboardingNavigation
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HandlerActivity : ComponentActivity() {

    private var lastBackPressTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HouseholdRechargeTheme(dynamicColor = false) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    OnboardingNavigation()
                }
            }
        }

        onBackPressedDispatcher.addCallback(backPressCallBack)
    }

    private val backPressCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val currentTime = System.currentTimeMillis()

            if (currentTime - lastBackPressTime < 2000) {
                finish()
            } else {
                lastBackPressTime = currentTime

                Toast.makeText(
                    this@HandlerActivity,
                    "Press back again to exit",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}