package com.raza.householdrecharge.ui.splash

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raza.householdrecharge.R
import com.raza.householdrecharge.common.getViewModel
import com.raza.householdrecharge.ui.theme.HouseholdRechargeTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import com.raza.householdrecharge.core.result.Result

@Composable
fun SplashScreen(
    openLogin: () -> Unit,
    openSetupHousehold: () -> Unit,
    openDashboard: () -> Unit,

    viewModel: SplashViewModel
) {

    LaunchedEffect(Unit) {
        delay(2000.milliseconds)

        val destination = viewModel.getStartDestination()

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
    }
}

@Composable
fun SplashContent() {
    val viewModel =
        getViewModel(SplashViewModel::class.java)

    HouseholdRechargeTheme(dynamicColor = false) {
        SplashScreen(
            openLogin = {},
            openSetupHousehold = {},
            openDashboard = {},
            viewModel = viewModel
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SplashScreenLightPreview() {
    SplashContent()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SplashScreenDarkPreview() {
    SplashContent()
}