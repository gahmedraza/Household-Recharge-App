package com.raza.householdrecharge.presentation.auth

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
import com.raza.householdrecharge.core.logging.log
import com.raza.householdrecharge.presentation.components.AppCard
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onSuccess: () -> Unit = {},
    onSignIn: () -> Unit = {}
) {
    var shouldProceed by remember { mutableStateOf(false) }
    var signupStatus by remember { mutableStateOf("") }


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
                        viewModel.accountName = it.trim()
                    },

                    label = {
                        Text("Name")
                    },

                    value = viewModel.accountName
                )

                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),

                    onValueChange = {
                        viewModel.mobileNumber = it.trim()
                    },

                    label = {
                        Text("Mobile Number")
                    },

                    value = viewModel.mobileNumber
                )

                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),

                    onValueChange = {
                        viewModel.password = it.trim()
                    },

                    label = {
                        Text("Password")
                    },

                    value = viewModel.password
                )

                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),

                    enabled = !shouldProceed,

                    onClick = {

                        viewModel.registerAndAddAccount(
                            onSuccess = { userId ->

                                signupStatus = "account creation success"
                                shouldProceed = true

                                log("user created with id= $userId")
                            },
                            onFailure = { message ->

                                signupStatus = "account creation failure\n$message"
                                shouldProceed = false

                                log("response= $message")
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
                    modifier = Modifier.clickable { onSignIn() }
                )

                Spacer(modifier = Modifier.padding(20.dp))

                if (viewModel.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(20.dp))

                Text(
                    text = signupStatus,
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
fun SignupScreenContent() {
    HouseholdRechargeTheme(dynamicColor = false) {
        RegisterScreen()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SignupScreenDarkPreview() {
    SignupScreenContent()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SignupScreenLightPreview() {
    SignupScreenContent()
}