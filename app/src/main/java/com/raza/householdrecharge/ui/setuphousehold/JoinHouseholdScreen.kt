package com.raza.householdrecharge.ui.setuphousehold

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raza.householdrecharge.common.getViewModel
import com.raza.householdrecharge.util.cleanString

@Composable
fun JoinHouseholdScreen(
    viewModel: HouseholdViewModel,
    onSuccess: (String) -> Unit,
    onFailure: () -> Unit
) {
    Scaffold { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {

            JoinHouseholdBody(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(
                        start = 10.dp,
                        end = 10.dp
                    ),

                viewModel = viewModel,

                onSuccess = { householdName ->
                    onSuccess(householdName)
                },

                onFailure = {
                    onFailure()
                }
            )
        }
    }
}

@Composable
fun JoinHouseholdBody(
    modifier: Modifier,
    viewModel: HouseholdViewModel,
    onSuccess: (String) -> Unit,
    onFailure: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {

        Column(
            modifier = modifier
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                modifier = modifier,
                text = "Let's get you added to the household",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                modifier = modifier,
                text = "Enter your invitation code",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = modifier,

                label = {
                    Text("Invitation Code")
                },

                onValueChange = {
                    viewModel.invitationCode = it

                },

                value = viewModel.invitationCode
            )

            Spacer(modifier = Modifier.height(42.dp))

            OutlinedButton(
                modifier = modifier,

                enabled = !viewModel.isLoading,

                onClick = {

                    viewModel.validateInvitationCode(
                        invitationCode = viewModel.invitationCode,

                        onSuccess = { household ->
                            onSuccess(household?.householdName.cleanString())
                        },
                        onFailure = {
                            onFailure()
                        }
                    )
                }) {

                Text("Join Household")
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun JoinHouseholdContent() {
    val viewModel =
        getViewModel(HouseholdViewModel::class.java)

    JoinHouseholdScreen(
        viewModel = viewModel,

        onSuccess = {

        },

        onFailure = {

        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun JoinHouseholdScreenDarkPreview() {
    JoinHouseholdContent()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun JoinHouseholdScreenLightPreview() {
    JoinHouseholdContent()
}