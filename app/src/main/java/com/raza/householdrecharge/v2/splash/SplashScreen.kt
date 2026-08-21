package com.raza.householdrecharge.v2.splash

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
import com.raza.householdrecharge.ui.theme.HouseholdRechargeTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(2000.milliseconds)
        onSplashFinished()
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

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SplashScreenDarkPreview() {
    HouseholdRechargeTheme(dynamicColor = false) {
        SplashScreen {}
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SplashScreenLightPreview() {
    HouseholdRechargeTheme(dynamicColor = false) {
        SplashScreen {}
    }
}