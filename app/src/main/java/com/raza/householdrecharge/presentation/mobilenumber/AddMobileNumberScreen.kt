package com.raza.householdrecharge.presentation.mobilenumber

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.R
import com.raza.householdrecharge.presentation.components.AppCard
import com.raza.householdrecharge.presentation.preview.getViewModel
import com.raza.householdrecharge.util.cleanString

@Composable
fun AddMobileNumberScreen(
    viewModel: MobileNumberViewModel,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {

    val modifier = Modifier
        .fillMaxWidth()

    Body(
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
    viewModel: MobileNumberViewModel,
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
                        Text("Mobile Number")
                    },

                    onValueChange = {
                        viewModel.mobileNumber2 = it
                    },

                    value = viewModel.mobileNumber2,

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(Modifier.height(40.dp))

                OutlinedButton(
                    modifier = modifier,

                    enabled = !shouldProceed,

                    onClick = {
                        viewModel.addMobileNumber(

                            onSuccess = {
                                signinStatus = "mobile number has been added"
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
                    Text("Add Mobile Number")
                }

                Spacer(modifier = Modifier.padding(10.dp))

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

                Spacer(Modifier.height(40.dp))

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
fun Content(viewModel: MobileNumberViewModel) {
    val viewModel =
        getViewModel(MobileNumberViewModel::class.java)

    AddMobileNumberScreen(
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

