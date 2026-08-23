package com.raza.householdrecharge.v2.addhousehold

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.v2.TitleBar

@Composable
fun AddHouseholdScreen(
    viewModel: AddHouseholdViewModel,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    Scaffold(
        topBar = {
            TitleBar("Add Household")
        }
    ) { paddingValues ->

        Body(
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
fun Body(
    modifier: Modifier,
    viewModel: AddHouseholdViewModel,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
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

        Button(
            modifier = modifier,

            onClick = {

                viewModel.onAddHousehold(
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
fun Content() {
    AddHouseholdScreen(
        viewModel = viewModel(),

        onSuccess = {

        },

        onFailure = {

        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddHouseholdScreenDarkPreview() {
    Content()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AddHouseholdScreenLightPreview() {
    Content()
}