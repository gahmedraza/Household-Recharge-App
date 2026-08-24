package com.raza.householdrecharge.v2.auth

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.v2.addhousehold.AddHouseholdViewModel
import com.raza.householdrecharge.v2.common.AppSnackbar
import com.raza.householdrecharge.v2.common.SnackbarUtil
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    viewModel: SignupViewModel,
    householdViewModel: AddHouseholdViewModel,
    onSignup: () -> Unit,
    onSignIn: () -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            AppSnackbar(hostState = snackbarHostState)
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(6.dp),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),

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

                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),

                    onValueChange = {
                        viewModel.password = it
                    },

                    label = {
                        Text("Confirm Password")
                    },

                    value = viewModel.password
                )

                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),

                    onValueChange = {
                        viewModel.household = viewModel.household.copy(
                            name = it
                        )
                    },

                    label = {
                        Text("Household Name")
                    },

                    value = viewModel.household.name ?: ""
                )

                Spacer(modifier = Modifier.padding(60.dp))

                if (viewModel.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(60.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),

                    onClick = {

                        viewModel.signup(
                            onSuccess = { userId ->

                                householdViewModel.onAddHousehold(
                                    userId = userId,

                                    householdName = viewModel.household.name ?: "",

                                    onSuccess = { householdId ->

                                        scope.launch {
                                            SnackbarUtil.show(
                                                snackbarHostState = snackbarHostState,
                                                message = "User Created with id= $householdId"
                                            )
                                        }

                                        onSignup()
                                    },
                                    onFailure = { message ->
                                        scope.launch {
                                            SnackbarUtil.show(
                                                snackbarHostState = snackbarHostState,
                                                message = "response= $message"
                                            )
                                        }

                                        Log.d("TAG", "Sign In Failed")
                                    }
                                )
                            },
                            onFailure = { message ->
                                scope.launch {
                                    SnackbarUtil.show(
                                        snackbarHostState = snackbarHostState,
                                        message = "response= $message"
                                    )
                                }

                                Log.d("TAG", "Sign In Failed")
                            }
                        )
                    }
                ) {
                    Text("Signup")
                }

                Spacer(modifier = Modifier.padding(20.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),

                    onClick = {
                        onSignIn()
                    }
                ) {
                    Text("SignIn")
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun darkPreviewSignup() {
    SignupContent()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun lightPreviewSignup() {
    SignupContent()
}

@Composable
fun SignupContent() {
    SignupScreen(
        viewModel = viewModel(),
        householdViewModel = viewModel(),
        onSignup = {

        },
        onSignIn = {

        })
}