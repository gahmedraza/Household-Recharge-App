package com.raza.householdrecharge.v2.addrecharge

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
import com.raza.householdrecharge.v2.common.AppDatePicker
import com.raza.householdrecharge.v2.common.TitleBar


@Composable
fun AddRechargeHistoryScreen(
    memberId: String,
    mobileNumber: String,
    viewModel: AddRechargeHistoryViewModel,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    Scaffold(
        topBar = {
            TitleBar("Add Recharge History")
        }
    ) { paddingValues ->

        val modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)

        Body(
            memberId = memberId,
            mobileNumber = mobileNumber,
            viewModel = viewModel,
            modifier = modifier,
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
    memberId: String,
    mobileNumber: String,
    viewModel: AddRechargeHistoryViewModel,
    modifier: Modifier,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {

    Column(modifier = modifier) {

        OutlinedTextField(
            modifier = modifier,

            label = {
                Text("Amount")
            },

            onValueChange = {
                viewModel.amount = it
            },

            value = viewModel.amount
        )

        Spacer(Modifier.height(4.dp))

        AppDatePicker(
            modifier = modifier,

            label = "Recharge Date",

            onDateSelected = {
                viewModel.date = it.toString()
            },

            value = viewModel.date,
        )

        Spacer(Modifier.height(4.dp))

        OutlinedTextField(
            modifier = modifier,

            label = {
                Text("Recharged By")
            },

            onValueChange = {
                viewModel.rechargedBy = it
            },

            value = viewModel.rechargedBy
        )

        Spacer(Modifier.height(4.dp))

        OutlinedButton(
            modifier = modifier,

            onClick = {
                viewModel.addRechargeHistory(
                    memberId = memberId,
                    mobileNumber = mobileNumber,
                    onSuccess = {
                        onSuccess()
                    },
                    onFailure = {
                        onFailure()
                    }
                )
            }
        ) {
            Text("Add Recharge")
        }
    }
}

@Composable
fun Content(viewModel: AddRechargeHistoryViewModel) {
    AddRechargeHistoryScreen(
        memberId = "",
        mobileNumber = "",
        viewModel = viewModel,
        onSuccess = {},
        onFailure = {}
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddRechargeHistoryScreenDarkPreview() {
    Content(viewModel = viewModel())
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AddRechargeHistoryScreenLightPreview() {
    Content(viewModel = viewModel())
}

