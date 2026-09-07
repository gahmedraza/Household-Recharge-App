package com.raza.householdrecharge.ui.addrecharge

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.common.AppCard
import com.raza.householdrecharge.common.AppDatePicker
import com.raza.householdrecharge.common.getViewModel

@Composable
fun AddRechargeScreen(
    memberId: String,
    mobileNumber: String,
    viewModel: AddRechargeViewModel,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {

    val modifier = Modifier
        .fillMaxWidth()

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

@Composable
fun Body(
    memberId: String,
    mobileNumber: String,
    viewModel: AddRechargeViewModel,
    modifier: Modifier,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {

    AppCard {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    modifier = modifier,

                    label = {
                        Text("Amount")
                    },

                    onValueChange = {
                        viewModel.amount = it
                    },

                    value = viewModel.amount,

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(Modifier.height(20.dp))

                AppDatePicker(
                    modifier = modifier,

                    label = "Recharge Date",

                    onDateSelected = {
                        viewModel.date = it.toString()
                    },

                    value = viewModel.date,
                )

                Spacer(Modifier.height(20.dp))

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

                Spacer(Modifier.height(40.dp))

                OutlinedButton(
                    modifier = modifier,

                    onClick = {
                        viewModel.addRecharge(
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
    }
}

@Composable
fun Content(viewModel: AddRechargeViewModel) {
    val viewModel =
        getViewModel(AddRechargeViewModel::class.java)

    AddRechargeScreen(
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

