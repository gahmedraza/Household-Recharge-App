package com.raza.householdrecharge.presentation.splash

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raza.householdrecharge.R
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(
    openLogin: () -> Unit = {},
    openSetupHousehold: () -> Unit = {},
    openDashboard: () -> Unit = {},
    viewmodel: SplashViewModel = hiltViewModel()
) {

    val splashUIState by viewmodel.splashUIState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewmodel.getStartDestination()
        delay(200.milliseconds)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = stringResource(R.string.logo_message),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displayLarge
        )

        splashUIState.destination?.let { destination ->

            when(destination) {

                is SplashDestination.Login -> {
                    openLogin()
                }

                is SplashDestination.SetupHousehold -> {
                    openSetupHousehold()
                }

                is SplashDestination.Dashboard -> {
                    openDashboard()
                }
            }
        }
    }
}

@Composable
fun SplashContent() {
    HouseholdRechargeTheme(dynamicColor = false) {
        SplashScreen()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SplashScreenDarkPreview() {
    SplashContent()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SplashScreenLightPreview() {
    SplashContent()
}