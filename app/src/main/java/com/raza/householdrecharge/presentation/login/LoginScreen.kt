package com.raza.householdrecharge.presentation.login

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewmodel: LoginViewModel = hiltViewModel(),
    onRegister: () -> Unit = {},
    onLoginCompletion: () -> Unit = {},
    onBoardingNotComplete: () -> Unit = {}
) {
    val loginUIState by viewmodel.loginUIState.collectAsStateWithLifecycle()
    //todo extract to common
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
                        viewmodel.onMobileNumberChanged(it.trim())
                        viewmodel.resetMobileNumberError()
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
                    },

                    singleLine = true
                )

                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),

                    onValueChange = {
                        viewmodel.onPasswordChanged(it.trim())
                        viewmodel.resetPasswordError()
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
                    },

                    singleLine = true
                )

                Spacer(modifier = Modifier.padding(60.dp))

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),

                    enabled = !loginUIState.shouldProceed,

                    onClick = {

                        //make the api call
                        viewmodel.login()
                    }
                ) {
                    Text(stringResource(R.string.login))
                }

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),

                    enabled = loginUIState.shouldProceed,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(loginUIState.shouldProceed) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        }
                    ),

                    onClick = {
                        scope.launch {
                            if(viewmodel.isOnboardingComplete()) {

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
                    text = loginUIState.apiResponse,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (loginUIState.shouldProceed) {
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