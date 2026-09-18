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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.data.remote.dto.AuthDto
import com.raza.householdrecharge.presentation.common.MobileNumberValidator
import com.raza.householdrecharge.presentation.components.AppCard
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onRegister: () -> Unit = {},
    onLoginCompletion: () -> Unit = {},
    onBoardingNotComplete: () -> Unit = {}
) {
    val loginUIState = viewModel.loginUIState
    var shouldProceed by rememberSaveable { mutableStateOf(false) }
    var apiStatus by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()

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
                    text = stringResource(R.string.login_header),
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
                        loginUIState.mobileNumber = it.trim()
                        loginUIState.mobileNumberError = ""
                    },

                    label = {
                        Text(stringResource(R.string.mobile_number))
                    },

                    value = loginUIState.mobileNumber,

                    isError = loginUIState.mobileNumberError.isNotEmpty(),

                    supportingText = {
                        if(loginUIState.mobileNumberError.isNotEmpty()) {
                            Text(loginUIState.mobileNumberError)
                        }
                    }
                )

                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),

                    onValueChange = {
                        loginUIState.password = it.trim()
                        loginUIState.passwordError = ""
                    },

                    label = {
                        Text(stringResource(R.string.password))
                    },

                    value = loginUIState.password,

                    isError = loginUIState.passwordError.isNotEmpty(),

                    supportingText = {
                        if(loginUIState.passwordError.isNotEmpty()) {
                            Text(loginUIState.passwordError)
                        }
                    }
                )

                Spacer(modifier = Modifier.padding(60.dp))

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),

                    enabled = !shouldProceed,

                    onClick = {

                        //validate the input fields
                        loginUIState.mobileNumberError = MobileNumberValidator.validateMobileNumber(loginUIState.mobileNumber, loginUIState.mobileNumberError)

                        if(loginUIState.password.length < 8) {
                            loginUIState.passwordError = "Password must contain at least 8 characters"
                        }

                        if(loginUIState.mobileNumberError.isNotEmpty()||loginUIState.passwordError.isNotEmpty()) {
                            return@OutlinedButton
                        }

                        //make the api call
                        viewModel.login2(
                            authDto = AuthDto(
                                accountName = null,
                                mobileNumber = loginUIState.mobileNumber,
                                password = loginUIState.password
                            ),

                            onSuccess = { userId ->

                                apiStatus = "login success"
                                shouldProceed = true

                                Logger.log("user logged in with id= $userId")
                            },
                            onFailure = { message ->

                                apiStatus = "login failure\n$message"
                                shouldProceed = false

                                Logger.log("response= $message")
                            })
                    }
                ) {
                    Text(stringResource(R.string.login))
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
                        scope.launch {
                            if(viewModel.isOnboardingComplete()) {

                                onLoginCompletion()
                            } else {

                                onBoardingNotComplete()
                            }
                        }
                    }
                ) {
                    Text(stringResource(R.string.proceed))
                }

                Spacer(modifier = Modifier.padding(20.dp))

                Text(
                    text = stringResource(R.string.create_account),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onRegister() }
                )

                Spacer(modifier = Modifier.padding(20.dp))

                if (loginUIState.isLoading) {
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
fun LoginScreenContent() {
    HouseholdRechargeTheme(dynamicColor = false) {
        LoginScreen()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LoginScreenDarkPreview() {
    LoginScreenContent()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun LoginScreenLightPreview() {
    LoginScreenContent()
}