package com.raza.householdrecharge.presentation.setuphousehold

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.raza.householdrecharge.presentation.components.AppCard
import com.raza.householdrecharge.presentation.components.LargeBodyText
import com.raza.householdrecharge.presentation.components.LargeDisplayText
import com.raza.householdrecharge.presentation.components.SmallHeadlineText
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme
import com.raza.householdrecharge.util.cleanString

@Composable
fun ConfirmHouseholdScreen(
    householdName: String? = "",
    householdId: String? = "",
    invitationCode: String? = "",
    viewModel: HouseholdViewModel = hiltViewModel(),
    onSuccess:() -> Unit = {},
    onFailure:() -> Unit = {}
) {

    var shouldProceed by rememberSaveable { mutableStateOf(false) }
    var signinStatus by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {

        val paddingValues = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 40.dp,
            bottom = 40.dp
        )

        AppCard(
            paddingValues = paddingValues,
            elevation = 4.dp,
            cornerSize = 4.dp
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                SmallHeadlineText(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    text = "Join Household",
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(Modifier.height(40.dp))

                LargeBodyText(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    text = "You are invited to join",
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(Modifier.height(60.dp))

                LargeDisplayText(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    text = householdName.cleanString(),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(60.dp))

                LargeBodyText(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    text = "Do you want to join this household?",
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(Modifier.height(60.dp))

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 20.dp,
                            end = 20.dp),

                    enabled = !shouldProceed,

                    onClick = {

                        viewModel.onJoinHousehold(
                            invitationCode = invitationCode.cleanString(),
                            householdId = householdId.cleanString(),
                            onSuccess = {

                                signinStatus = "You have been added to the household"
                                shouldProceed = true
                            },
                            onFailure = {

                                signinStatus = "Failure"
                                shouldProceed = false

                                onFailure()
                            }
                        )
                    }
                ) {
                    Text(
                        text = "Join"
                    )
                }

                Spacer(Modifier.height(10.dp))

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 20.dp,
                            end = 20.dp),

                    enabled = shouldProceed,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(shouldProceed) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        }
                    ),

                    onClick = {

                        onSuccess()
                    }
                ) {
                    Text("Proceed")
                }

                Spacer(modifier = Modifier.padding(20.dp))

                if (viewModel.isLoading) {
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
                    text = signinStatus,
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

@Composable
fun ConfirmHouseholdScreenContent() {
    HouseholdRechargeTheme(dynamicColor = false) {
        ConfirmHouseholdScreen()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ConfirmHouseholdScreenDarkPreview() {
    ConfirmHouseholdScreenContent()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun ConfirmHouseholdScreenLightPreview() {
    ConfirmHouseholdScreenContent()
}