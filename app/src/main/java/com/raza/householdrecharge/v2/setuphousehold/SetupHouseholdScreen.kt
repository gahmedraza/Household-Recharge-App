package com.raza.householdrecharge.v2.setuphousehold

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.v2.TitleBar

@Composable
fun SetupHouseholdScreen(
    viewModel: HouseholdViewModel,
    onCreateHousehold: () -> Unit,
    onJoinHousehold: () -> Unit
) {
    Scaffold(
        topBar = {
            TitleBar("Household Setup")
        }
    ) { paddingValues ->

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

@Composable
fun SetupHouseholdBody(
    modifier: Modifier,
    viewModel: HouseholdViewModel,
    onCreateHousehold: () -> Unit,
    onJoinHousehold: () -> Unit
) {
    Column(
        modifier = modifier
    ) {

        Text(
            text = "How would you like to continue?"
        )

        Button(
            modifier = modifier,

            onClick = {

                onCreateHousehold()
            }) {

            Text("Create Household")
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = "Start a new household and \nbecome its manager.")

        Button(
            modifier = modifier,

            onClick = {

                onJoinHousehold()
            }) {

            Text("Join Household")
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = "Join a household using an \ninvitation code")
    }
}

@Composable
fun SetupHouseholdContent() {
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
fun SetupHouseholdScreenDarkPreview() {
    SetupHouseholdContent()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SetupHouseholdScreenLightPreview() {
    SetupHouseholdContent()
}