package com.raza.householdrecharge.presentation.setuphousehold

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.raza.householdrecharge.domain.model.FindHouseholdResponse
import com.raza.householdrecharge.presentation.components.AppCard
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme
import com.raza.householdrecharge.util.cleanString

@Composable
fun FindHouseholdScreen(
    viewmodel: HouseholdViewModel = hiltViewModel(),
    onSuccess: (FindHouseholdResponse?) -> Unit = { a -> },
    onFailure: () -> Unit = {}
) {

    val householdUIState = viewmodel.householdUIState
    Scaffold { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {

            val modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(
                    start = 10.dp,
                    end = 10.dp
                )

            var shouldProceed by rememberSaveable { mutableStateOf(false) }
            var apiStatus by rememberSaveable { mutableStateOf("") }
            var findHouseholdResponse: FindHouseholdResponse? = null

            AppCard(Modifier.fillMaxWidth()) {

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
                            householdUIState.invitationCode = it.trim()

                        },

                        value = householdUIState.invitationCode
                    )

                    Spacer(modifier = Modifier.height(42.dp))

                    OutlinedButton(
                        modifier = modifier,

                        enabled = !householdUIState.isLoading,

                        onClick = {

                            viewmodel.findHousehold(
                                invitationCode = householdUIState.invitationCode,

                                onSuccess = { response ->
                                    findHouseholdResponse = response

                                    shouldProceed = true
                                    apiStatus = "invitation code found"
                                },
                                onFailure = { message ->
                                    shouldProceed = false
                                    apiStatus = message.cleanString()

                                    onFailure()
                                }
                            )
                        }) {

                        Text("Find Household")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),

                        enabled = shouldProceed,

                        colors = ButtonDefaults.buttonColors(
                            containerColor = if(shouldProceed) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant
                            }
                        ),

                        onClick = {

                            onSuccess(findHouseholdResponse)
                        }
                    ) {
                        Text("Proceed")
                    }

                    Spacer(modifier = Modifier.padding(20.dp))

                    if (householdUIState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(24.dp)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                    Spacer(modifier = Modifier.padding(20.dp))

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        text = apiStatus,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (shouldProceed) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.error
                        },
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.padding(20.dp))
                }
            }
        }
    }
}

@Composable
fun FindHouseholdScreenContent() {
    HouseholdRechargeTheme(dynamicColor = false) {
        FindHouseholdScreen()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun FindHouseholdScreenDarkPreview() {
    FindHouseholdScreenContent()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun FindHouseholdScreenLightPreview() {
    FindHouseholdScreenContent()
}