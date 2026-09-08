package com.raza.householdrecharge.presentation.setuphousehold

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.raza.householdrecharge.presentation.components.AppCard
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme

@Composable
fun SetupHouseholdScreen(
    viewModel: HouseholdViewModel = hiltViewModel(),
    onCreateHousehold: () -> Unit = {},
    onJoinHousehold: () -> Unit = {}
) {
    Scaffold { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {
            SetupHouseholdBody(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(
                        top = 10.dp,
                        bottom = 10.dp,
                        start = 10.dp,
                        end = 10.dp
                    ),

                viewModel = viewModel,

                onCreateHousehold = {
                    onCreateHousehold()
                },

                onJoinHousehold = {
                    onJoinHousehold()
                }
            )
        }
    }
}

@Composable
fun SetupHouseholdBody(
    modifier: Modifier,
    viewModel: HouseholdViewModel,
    onCreateHousehold: () -> Unit,
    onJoinHousehold: () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Set up your household",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Create a new household or join an existing one.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(42.dp))

        AppCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Create a household",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Start a new household and become its manager.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),

                    onClick = {
                        onCreateHousehold()
                    }) {

                    Text(
                        text = "Create Household",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Join a household",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Join an existing household using an invitation code.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),

                    onClick = {

                        onJoinHousehold()
                    }) {

                    Text(
                        text = "Join Household",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun SetupHouseholdContent() {
    HouseholdRechargeTheme(dynamicColor = false) {
        SetupHouseholdScreen()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SetupHouseholdScreenLightPreview() {
    SetupHouseholdContent()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SetupHouseholdScreenDarkPreview() {
    SetupHouseholdContent()
}