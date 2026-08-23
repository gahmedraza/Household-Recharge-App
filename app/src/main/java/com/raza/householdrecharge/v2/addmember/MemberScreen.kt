package com.raza.householdrecharge.v2.addmember

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toString
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.v2.TitleBar

@Composable
fun MemberScreen(
    viewModel: MemberViewModel,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {

    Scaffold(
        topBar = {
            TitleBar("Add Household Member")
        },

        ) { paddingValues ->

        Body(
            viewModel = viewModel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                    top = 10.dp,
                    bottom = 10.dp
                )
        )
    }
}

@Composable
fun Body(
    viewModel: MemberViewModel,
    modifier: Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        OutlinedTextField(
            modifier = modifier,
            label = {
                Text("Name")
            },
            onValueChange = {
                viewModel.name = it
            },
            value = viewModel.name
        )

        Spacer(modifier = Modifier.padding(12.dp))

        OutlinedTextField(
            modifier = modifier,
            label = {
                Text("Mobile Number")
            },
            onValueChange = {
                viewModel.mobileNumber = it
            },
            value = viewModel.mobileNumber
        )

        OutlinedTextField(
            modifier = modifier,
            label = {
                Text("Last Recharge Date")
            },
            onValueChange = {
                viewModel.lastRechargeDate = it.toLongOrNull() ?: 0
            },
            value = viewModel.lastRechargeDate.toString()
        )

        OutlinedTextField(
            modifier = modifier,
            label = {
                Text("Plan Amount")
            },
            onValueChange = {
                viewModel.planAmount = it
            },
            value = viewModel.planAmount
        )

        OutlinedTextField(
            modifier = modifier,
            label = {
                Text("Plan Expiry Date")
            },
            onValueChange = {
                viewModel.planExpiryDate = it.toLongOrNull() ?: 0
            },
            value = viewModel.planExpiryDate.toString()
        )

        OutlinedTextField(
            modifier = modifier,
            label = {
                Text("Plan Duration Days")
            },
            onValueChange = {
                viewModel.planDurationDays = it.toIntOrNull() ?: 0
            },
            value = viewModel.planDurationDays.toString()
        )

        OutlinedTextField(
            modifier = modifier,
            label = {
                Text("Days to Expiry")
            },
            onValueChange = {
                viewModel.daysToExpiry = it
            },
            value = viewModel.daysToExpiry
        )

        OutlinedTextField(
            modifier = modifier,
            label = {
                Text("Recharge Requested")
            },
            onValueChange = {
                viewModel.rechargeRequested = it
            },
            value = viewModel.rechargeRequested
        )

        Button(
            modifier = modifier,

            onClick = {
                viewModel.onAddMember(
                    onSuccess = {

                    },

                    onFailure = {

                    }
                )
            }
        ) {
            Text("Update")
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun darkPreviewSignIn() {
    Content()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun lightPreviewSignIn() {
    Content()
}

@Composable
fun Content() {
    MemberScreen(
        viewModel = viewModel(),
        onSuccess = {},
        onFailure = {}
    )
}