package com.raza.householdrecharge.presentation.addrecharge

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.raza.householdrecharge.R
import com.raza.householdrecharge.presentation.components.AppCard
import com.raza.householdrecharge.presentation.components.AppDatePicker
import com.raza.householdrecharge.util.cleanString

@Composable
fun AddRechargeScreen(
    mobileNumber: String = "",
    viewModel: AddRechargeViewModel = hiltViewModel(),
    onSuccess: () -> Unit = {},
    onFailure: () -> Unit = {}
) {

    val modifier = Modifier
        .fillMaxWidth()

    Body(
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

//TODO merge body with main fn
@Composable
fun Body(
    mobileNumber: String,
    viewModel: AddRechargeViewModel,
    modifier: Modifier,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {

    var shouldProceed by rememberSaveable { mutableStateOf(false) }
    var signinStatus by rememberSaveable { mutableStateOf("") }

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
                        Text("Description")
                    },

                    onValueChange = {
                        viewModel.rechargeDescription = it
                    },

                    value = viewModel.rechargeDescription
                )

                Spacer(Modifier.height(20.dp))

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

                AppDatePicker(
                    modifier = modifier,

                    label = "Expiry Date",

                    onDateSelected = {
                        viewModel.planExpiryDate = it.toString()
                    },

                    value = viewModel.planExpiryDate,
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

                    enabled = !shouldProceed,

                    onClick = {
                        viewModel.addRecharge(
                            mobileNumber = mobileNumber,
                            onSuccess = {

                                signinStatus = "recharge has been added"
                                shouldProceed = true

                            },
                            onFailure = { message ->

                                signinStatus = message.cleanString()
                                shouldProceed = false
                                onFailure()
                            }
                        )
                    }
                ) {
                    Text("Add Recharge")
                }

                Spacer(Modifier.height(10.dp))

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),

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
                    Text(stringResource(R.string.proceed))
                }

                Spacer(modifier = Modifier.padding(20.dp))

                if (viewModel.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(20.dp))

                Text(
                    text = signinStatus,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (shouldProceed) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    },
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun Content() {
    AddRechargeScreen()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddRechargeHistoryScreenDarkPreview() {
    Content()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AddRechargeHistoryScreenLightPreview() {
    Content()
}

