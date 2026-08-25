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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.v2.common.TitleBar

@Composable
fun JoinHouseholdScreen(
    viewModel: HouseholdViewModel,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    Scaffold(
        topBar = {
            TitleBar("Join Household")
        }
    ) { paddingValues ->

        JoinHouseholdBody(
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
fun JoinHouseholdBody(
    modifier: Modifier,
    viewModel: HouseholdViewModel,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Enter your invitation code",
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

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

        Spacer(modifier = Modifier.height(30.dp))

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

            Text("Join Household")
        }
    }
}

@Composable
fun JoinHouseholdContent() {
    JoinHouseholdScreen(
        viewModel = viewModel(),

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