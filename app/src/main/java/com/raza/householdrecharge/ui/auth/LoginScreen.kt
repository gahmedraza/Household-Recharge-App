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
import com.raza.householdrecharge.R
import com.raza.householdrecharge.common.AppCard
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
    var mobileNumberError by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf("") }
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
                        mobileNumber = it
                        mobileNumberError = ""
                    },

                    label = {
                        Text(stringResource(R.string.mobile_number))
                    },

                    value = mobileNumber,

                    isError = mobileNumberError.isNotEmpty(),

                    supportingText = {
                        if(mobileNumberError.isNotEmpty()) {
                            Text(mobileNumberError)
                        }
                    }
                )

                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),

                    onValueChange = {
                        password = it
                        passwordError = ""
                    },

                    label = {
                        Text(stringResource(R.string.password))
                    },

                    value = password,

                    isError = passwordError.isNotEmpty(),

                    supportingText = {
                        if(passwordError.isNotEmpty()) {
                            Text(passwordError)
                        }
                    }
                )

                Spacer(modifier = Modifier.padding(60.dp))

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),

                    enabled = !shouldProceed,

                    onClick = {

                        //validate the input fields
                        mobileNumberError = validateMobileNumber(mobileNumber, mobileNumberError)

                        if(password.length < 8) {
                            passwordError = "Password must contain at least 8 characters"
                        }

                        if(mobileNumberError.isNotEmpty()||passwordError.isNotEmpty()) {
                            return@OutlinedButton
                        }

                        //make the api call
                        viewModel.login(
                            authDto = AuthDto(
                                accountName = null,
                                mobileNumber = mobileNumber,
                                password = password
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

                        if(viewModel.isOnboardingComplete()) {

                            onSignInCompletion()
                        } else {

                            onBoardingNotComplete()
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

private fun validateMobileNumber(mobileNumber: String, mobileNumberError: String): String {
    var mobileNumberError1 = mobileNumberError
    if (mobileNumber.length != 10) {
        mobileNumberError1 = "Enter a valid mobile number"
    }
    return mobileNumberError1
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
    val viewModel = getViewModel(LoginViewModel::class.java)

    HouseholdRechargeTheme(dynamicColor = false) {
        LoginScreen(
            viewModel = viewModel,
            onSignup = {},
            onSignInCompletion = {},
            onBoardingNotComplete = {}
        )
    }
}