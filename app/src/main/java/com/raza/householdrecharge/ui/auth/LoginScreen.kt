package com.raza.householdrecharge.ui.auth

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raza.householdrecharge.R
import com.raza.householdrecharge.common.getViewModel
import com.raza.householdrecharge.common.log
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.ui.theme.HouseholdRechargeTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onSignup: () -> Unit,
    onSignInCompletion: () -> Unit,
    onBoardingNotComplete: () -> Unit
) {
    var mobileNumber by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var shouldProceed by remember { mutableStateOf(false) }
    var signinStatus by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {

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
                    text = stringResource(R.string.add),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),

                    onValueChange = {
                        viewModel.mobileNumber = it
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
                        viewModel.password = it
                    },

                    label = {
                        Text("Password")
                    },

                    value = viewModel.password
                )

                Spacer(modifier = Modifier.padding(60.dp))

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),

                    enabled = !shouldProceed,

                    onClick = {

                        viewModel.login(
                            //todo modify
                            authDto = AuthDto(
                                "",
                                "",
                                ""
                            ),

                            onSuccess = { userId ->

                                signinStatus = "login success"
                                shouldProceed = true

                                log("user logged in with id= $userId")
                            },
                            onFailure = { message ->

                                signinStatus = "login failure\n$message"
                                shouldProceed = false

                                log("response= $message")
                            })
                    }
                ) {
                    Text("Log In")
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

                        if(viewModel.isOnboardingComplete()) {

                            onSignInCompletion()
                        } else {

                            onBoardingNotComplete()
                        }
                    }
                ) {
                    Text("Proceed")
                }

                Spacer(modifier = Modifier.padding(20.dp))

                Text(
                    text = "Don't have an account? Create New",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onSignup() }
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

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun lightPreviewSignIn() {
    Content()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun darkPreviewSignIn() {
    Content()
}

@Composable
fun Content() {
    val viewModel =
        getViewModel(LoginViewModel::class.java)

    HouseholdRechargeTheme(dynamicColor = false) {
        LoginScreen(
            viewModel = viewModel,
            onSignup = {},
            onSignInCompletion = {},
            onBoardingNotComplete = {}
        )
    }
}