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
import com.google.firebase.auth.FirebaseAuth
import com.raza.householdrecharge.R
import com.raza.householdrecharge.v1.ui.theme.HouseholdRechargeTheme
import com.raza.householdrecharge.v2.common.getViewModel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(
    onUserFound: () -> Unit,
    onUserNotFound: () -> Unit,
    viewModel: SplashViewModel
) {

    LaunchedEffect(Unit) {
        delay(2000.milliseconds)

        if(viewModel.getAuthFlowStatus()) {
            onUserFound()

        } else {

            onUserNotFound()
        }

        /*if (FirebaseAuth.getInstance().currentUser == null) {
            onUserNotFound()

        } else {
            onUserFound()
        }*/
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
    val viewModel = getViewModel(SplashViewModel::class.java)

    HouseholdRechargeTheme(dynamicColor = false) {
        SplashScreen(
            onUserFound = {},
            onUserNotFound = {},
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