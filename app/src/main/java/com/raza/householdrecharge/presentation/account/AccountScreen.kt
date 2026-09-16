package com.raza.householdrecharge.presentation.account

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.raza.householdrecharge.presentation.components.AppCard
import com.raza.householdrecharge.presentation.components.TitleBar
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getAccount()
    }

    Scaffold(
        topBar = {
            TitleBar("Profile")
        },

        ) { paddingValues ->

        AppCard(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.CenterHorizontally)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {

                    Spacer(modifier = Modifier.height(20.dp))

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "profile picture",
                        modifier = Modifier.size(60.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Profile Name"
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = viewModel.profileName
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Household Name"
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = viewModel.profileHousehold
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun AccountContent() {
    HouseholdRechargeTheme {
        AccountScreen()
    }
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
fun AccountScreenLightPreview() {
    AccountContent()
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun AccountScreenDarkPreview() {
    AccountContent()
}