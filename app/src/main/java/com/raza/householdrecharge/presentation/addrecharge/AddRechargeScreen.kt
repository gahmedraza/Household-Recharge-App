package com.raza.householdrecharge.presentation.addrecharge

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raza.householdrecharge.R
import com.raza.householdrecharge.presentation.components.AppCard
import com.raza.householdrecharge.presentation.components.AppDatePicker
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme
import com.raza.householdrecharge.util.cleanString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRechargeScreen(
    mobileNumber: String = "",
    id: String = "",
    viewmodel: AddRechargeViewModel = hiltViewModel(),
    onSuccess: () -> Unit = {},
    onFailure: () -> Unit = {}
) {

    val addRechargeUIState by viewmodel.addRechargeUIState.collectAsStateWithLifecycle()
    var shouldProceed by rememberSaveable { mutableStateOf(false) }
    var apiStatus by rememberSaveable { mutableStateOf("") }
    val modifier = Modifier.fillMaxWidth()

    AppCard {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
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
                        viewmodel.onRechargeDescriptionChanged(it)
                        viewmodel.resetRechargeDescriptionError()
                    },

                    value = addRechargeUIState.rechargeDescription,

                    isError = addRechargeUIState.rechargeDescriptionError.isNotEmpty(),

                    supportingText = {
                        if(addRechargeUIState.rechargeDescriptionError.isNotEmpty()) {
                            Text(addRechargeUIState.rechargeDescriptionError)
                        }
                    }
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = modifier,

                    label = {
                        Text("Amount")
                    },

                    onValueChange = {
                        viewmodel.onAmountChanged(it.trim())
                        viewmodel.resetAmountError()
                    },

                    value = addRechargeUIState.amount,

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),

                    isError = addRechargeUIState.amountError.isNotEmpty(),

                    supportingText = {
                        if(addRechargeUIState.amountError.isNotEmpty()) {
                            Text(addRechargeUIState.amountError)
                        }
                    }
                )

                Spacer(Modifier.height(20.dp))

                AppDatePicker(
                    modifier = modifier,

                    label = "Recharge Date",

                    onDateSelected = {
                        viewmodel.onDateChanged(it.toString())
                        viewmodel.resetDateError()
                    },

                    value = addRechargeUIState.date,

                    isError = addRechargeUIState.dateError.isNotEmpty(),

                    supportingText = {
                        if(addRechargeUIState.dateError.isNotEmpty()) {
                            Text(addRechargeUIState.dateError)
                        }
                    }
                )

                Spacer(Modifier.height(20.dp))

                AppDatePicker(
                    modifier = modifier,

                    value = addRechargeUIState.planExpiryDate,

                    onDateSelected = {
                        viewmodel.onPlanExpiryDateChanged(it.toString())
                        viewmodel.resetPlanExpiryDateError()
                    },

                    label = "Expiry Date",

                    isError = addRechargeUIState.planExpiryDateError.isNotEmpty(),

                    supportingText = {
                        if(addRechargeUIState.planExpiryDateError.isNotEmpty()) {
                            Text(addRechargeUIState.planExpiryDateError)
                        }
                    }
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = modifier,

                    label = {
                        Text("Recharged By")
                    },

                    onValueChange = {
                        viewmodel.onRechargedByChanged(it)
                        viewmodel.resetRechargedByError()
                    },

                    value = addRechargeUIState.rechargedBy,

                    isError = addRechargeUIState.rechargedByError.isNotEmpty(),

                    supportingText = {
                        if(addRechargeUIState.rechargedByError.isNotEmpty()) {
                            Text(addRechargeUIState.rechargedByError)
                        }
                    }
                )

                Spacer(Modifier.height(40.dp))

                OutlinedButton(
                    modifier = modifier,

                    enabled = !shouldProceed,

                    onClick = {
                        viewmodel.addRecharge(
                            mobileNumber = mobileNumber,
                            mobileNumberId = id,
                            onSuccess = {

                                apiStatus = "recharge has been added"
                                shouldProceed = true

                            },
                            onFailure = { message ->

                                apiStatus = message.cleanString()
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

                if (addRechargeUIState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(20.dp))

                Text(
                    text = apiStatus,
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

        if(addRechargeUIState.showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    viewmodel.onShowBottomSheetModified(false)
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("api response")

                    Text(addRechargeUIState.apiResponse)
                }
            }
        }
    }
}

@Composable
fun AddRechargeContent() {
    HouseholdRechargeTheme(dynamicColor = false) {
        AddRechargeScreen()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun AddRechargeScreenDarkPreview() {
    AddRechargeContent()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun AddRechargeScreenLightPreview() {
    AddRechargeContent()
}

