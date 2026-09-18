package com.raza.householdrecharge.presentation.settings

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.raza.householdrecharge.presentation.components.TitleBar
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    onLogout: () -> Unit = {},
    onInvitation: () -> Unit = {},
    onAccount: () -> Unit = {}
) {

    Scaffold(
        topBar = {
            TitleBar("Settings")
        }
    ) { paddingValues ->

        val modifier = Modifier.padding(paddingValues)

        Column(modifier = modifier) {
            val cellPadding = PaddingValues(
                start = 30.dp,
                end = 30.dp,
                top = 30.dp,
                bottom = 30.dp
            )

            Text(
                text = "Account",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onAccount()
                    }
                    .padding(cellPadding),

                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider(modifier = Modifier.height(1.dp))

            Text(
                text = "Invitations",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onInvitation()
                    }
                    .padding(cellPadding),

                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider(modifier = Modifier.height(1.dp))

            Text(
                text = "Logout",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.logout(
                            onSuccess = {
                                onLogout()
                            },
                            onFailure = {

                            }
                        )
                    }
                    .padding(cellPadding),
                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider(modifier = Modifier.height(1.dp))
        }
    }
}

@Composable
fun SettingContent() {
    HouseholdRechargeTheme(dynamicColor = false) {
        SettingScreen()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SettingScreenDarkPreview() {
    SettingContent()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SettingScreenLightPreview() {
    SettingContent()
}