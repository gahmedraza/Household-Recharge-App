package com.raza.householdrecharge.ui.setuphousehold

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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
import com.raza.householdrecharge.ui.theme.HouseholdRechargeTheme
import com.raza.householdrecharge.util.cleanString

@Composable
fun CreateHouseholdScreen(
    viewModel: HouseholdViewModel,
    onSuccess: () -> Unit,
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

            CreateHouseholdBody(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(
                        start = 10.dp,
                        end = 10.dp
                    ),

                viewModel = viewModel,

                onSuccess = {
                    onSuccess()
                },

                onFailure = {
                    onFailure()
                }
            )
        }
    }
}

@Composable
fun CreateHouseholdBody(
    modifier: Modifier,
    viewModel: HouseholdViewModel,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
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
                viewModel.household = viewModel.household.copy(
                    name = it
                )
            },

            value = viewModel.household.name ?: ""
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (viewModel.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            modifier = modifier,

            enabled = !viewModel.isLoading,

            onClick = {

                viewModel.onAddHousehold(
                    householdName = viewModel.household.name.cleanString(),

                    onSuccess = { householdId ->
                        Log.d("TAG", "success: $householdId")
                        onSuccess()
                    },
                    onFailure = { error ->
                        Log.d("TAG", "failure: $error")
                        onFailure()
                    }
                )
            }) {

            Text("Add")
        }
    }
}

@Composable
fun CreateHouseholdContent() {
    val viewModel =
        getViewModel(HouseholdViewModel::class.java)

    HouseholdRechargeTheme {
        CreateHouseholdScreen (
            viewModel = viewModel,

            onSuccess = {

            },

            onFailure = {

            }
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun CreateHouseholdScreenLightPreview() {
    CreateHouseholdContent()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CreateHouseholdScreenDarkPreview() {
    CreateHouseholdContent()
}