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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raza.householdrecharge.R
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateHouseholdScreen(
    viewmodel: HouseholdViewModel = hiltViewModel(),
    onSuccess: () -> Unit = {},
) {
    val householdUIState by viewmodel.householdUIState.collectAsStateWithLifecycle()

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

            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Let's create the household",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(42.dp))

                OutlinedTextField(
                    modifier = modifier,

                    label = {
                        Text("Household Name")
                    },

                    onValueChange = {
                        viewmodel.onHouseholdNameChanged(it.trim())
                        viewmodel.resetHouseholdNameError()
                    },

                    value = householdUIState.householdName,

                    isError = householdUIState.householdNameError.isNotEmpty(),

                    supportingText = {
                        if(householdUIState.householdNameError.isNotEmpty()) {
                            Text(householdUIState.householdNameError)
                        }
                    },

                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    modifier = modifier,

                    enabled = !householdUIState.shouldProceed,

                    onClick = {

                        viewmodel.onAddHousehold(
                            householdName = householdUIState.householdName
                        )
                    }) {

                    Text("Add")
                }

                //
                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),

                    enabled = householdUIState.shouldProceed,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(householdUIState.shouldProceed) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        }
                    ),

                    onClick = {
                        onSuccess()
                    }
                ) {
                    Text(stringResource(R.string.proceed))
                }

                Spacer(modifier = Modifier.padding(20.dp))

                if (householdUIState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(20.dp))

                Text(
                    text = householdUIState.apiResponse,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (householdUIState.shouldProceed) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    },
                    textAlign = TextAlign.Center
                )
                //
            }
        }
    }
}

@Composable
fun CreateHouseholdScreenContent() {
    HouseholdRechargeTheme(dynamicColor = false) {
        CreateHouseholdScreen ()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CreateHouseholdScreenDarkPreview() {
    CreateHouseholdScreenContent()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun CreateHouseholdScreenLightPreview() {
    CreateHouseholdScreenContent()
}