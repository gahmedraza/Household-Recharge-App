package com.raza.householdrecharge.presentation.addmobilenumber

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.R
import com.raza.householdrecharge.presentation.components.AppCard
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme
import com.raza.householdrecharge.util.cleanString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMobileNumberScreen(
    viewmodel: AddMobileNumberViewModel = hiltViewModel(),
    onSuccess: () -> Unit = {},
) {

    val addMobileNumberUIState by viewmodel.addMobileNumberUIState.collectAsStateWithLifecycle()
    val modifier = Modifier.fillMaxWidth()

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
                        viewmodel.onMobileNumberChanged(it.trim())
                        viewmodel.resetMobileNumberError()
                    },

                    value = addMobileNumberUIState.mobileNumber,

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),

                    isError = addMobileNumberUIState.mobileNumberError.isNotEmpty(),

                    supportingText = {
                        if(addMobileNumberUIState.mobileNumberError.isNotEmpty()) {
                            Text(addMobileNumberUIState.mobileNumberError)
                        }
                    },

                    singleLine = true
                )

                Spacer(Modifier.height(40.dp))

                OutlinedButton(
                    modifier = modifier,

                    enabled = !addMobileNumberUIState.shouldProceed,

                    onClick = {
                        viewmodel.addMobileNumber()
                    }
                ) {
                    Text("Add Mobile Number")
                }

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),

                    enabled = addMobileNumberUIState.shouldProceed,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(addMobileNumberUIState.shouldProceed) {
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

                if (addMobileNumberUIState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(20.dp))

                Text(
                    text = addMobileNumberUIState.apiResponse,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (addMobileNumberUIState.shouldProceed) {
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
fun AddMobileNumberContent(viewModel: AddMobileNumberViewModel) {
    HouseholdRechargeTheme(dynamicColor = false) {
        AddMobileNumberScreen()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun AddMobileNumberScreenDarkPreview() {
    AddMobileNumberContent(viewModel = viewModel())
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun AddMobileNumberScreenLightPreview() {
    AddMobileNumberContent(viewModel = viewModel())
}

