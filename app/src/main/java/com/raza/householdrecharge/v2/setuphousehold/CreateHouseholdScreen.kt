package com.raza.householdrecharge.v2.setuphousehold

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.v2.common.TitleBar

@Composable
fun CreateHouseholdScreen(
    viewModel: HouseholdViewModel,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    Scaffold(
        topBar = {
            TitleBar("Household Setup")
        }
    ) { paddingValues ->

        CreateHouseholdBody(
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

            onSuccess = {
                onSuccess()
            },

            onFailure = {
                onFailure()
            }
        )
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
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = modifier,

            label = {
                Text("Join Household")
            },

            onValueChange = {
                viewModel.household = viewModel.household.copy(
                    name = it
                )
            },

            value = viewModel.household.name ?: ""
        )

        Spacer(modifier = Modifier.height(10.dp))

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

        OutlinedButton(
            modifier = modifier,

            onClick = {

                viewModel.onAddHousehold(
                    userId = "0",

                    householdName = "",

                    onSuccess = {
                        onSuccess()
                    },
                    onFailure = {
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
    SetupHouseholdScreen(
        viewModel = viewModel(),

        onCreateHousehold = {

        },

        onJoinHousehold = {

        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateHouseholdScreenDarkPreview() {
    CreateHouseholdContent()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CreateHouseholdScreenLightPreview() {
    CreateHouseholdContent()
}