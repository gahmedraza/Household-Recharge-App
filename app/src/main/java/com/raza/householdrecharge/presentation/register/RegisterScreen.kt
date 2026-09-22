package com.raza.householdrecharge.presentation.register

import android.content.res.Configuration
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.presentation.components.AppCard
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme

@Composable
fun RegisterScreen(
    viewmodel: RegisterViewModel = hiltViewModel(),
    onSuccess: () -> Unit = {},
    onLogin: () -> Unit = {}
) {
    val registerUIState by viewmodel.registerUIState.collectAsStateWithLifecycle()
    var shouldProceed by remember { mutableStateOf(false) }
    var apiStatus by remember { mutableStateOf("") }

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

                Text(
                    text = "Let's create a new account for you",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),

                    onValueChange = {
                        viewmodel.onAccountNameChanged(it.trim())
                        viewmodel.resetAccountNameError()
                    },

                    label = {
                        Text("Name")
                    },

                    value = registerUIState.accountName,

                    isError = registerUIState.accountNameError.isNotEmpty(),

                    supportingText = {
                        if(registerUIState.accountNameError.isNotEmpty()) {
                            Text(registerUIState.accountNameError)
                        }
                    }
                )

                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),

                    onValueChange = {
                        viewmodel.onMobileNumberChanged(it.trim())
                        viewmodel.resetMobileNumberError()
                    },

                    label = {
                        Text("Mobile Number")
                    },

                    value = registerUIState.mobileNumber,

                    isError = registerUIState.mobileNumberError.isNotEmpty(),

                    supportingText = {
                        if(registerUIState.mobileNumberError.isNotEmpty()) {
                            Text(registerUIState.mobileNumberError)
                        }
                    }
                )

                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),

                    onValueChange = {
                        viewmodel.onPasswordChanged(it.trim())
                        viewmodel.resetPasswordError()
                    },

                    label = {
                        Text("Password")
                    },

                    value = registerUIState.password,

                    isError = registerUIState.passwordError.isNotEmpty(),

                    supportingText = {
                        if(registerUIState.passwordError.isNotEmpty()) {
                            Text(registerUIState.passwordError)
                        }
                    }
                )

                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),

                    enabled = !shouldProceed,

                    onClick = {

                        viewmodel.registerAndAddAccount(
                            onSuccess = { userId ->

                                apiStatus = "account creation success"
                                shouldProceed = true

                                Logger.log("user created with id= $userId")
                            },
                            onFailure = { message ->

                                apiStatus = "account creation failure\n$message"
                                shouldProceed = false

                                Logger.log("response= $message")
                            }
                        )
                    }
                ) {
                    Text("Create")
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
                    Text("Proceed")
                }

                Spacer(modifier = Modifier.padding(20.dp))

                Text(
                    text = "Already have an account? Log In",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onLogin() }
                )

                Spacer(modifier = Modifier.padding(20.dp))

                if (registerUIState.isLoading) {
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
    }
}

@Composable
fun RegisterScreenContent() {
    HouseholdRechargeTheme(dynamicColor = false) {
        RegisterScreen()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun RegisterScreenDarkPreview() {
    RegisterScreenContent()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun RegisterScreenLightPreview() {
    RegisterScreenContent()
}