package com.raza.householdrecharge.presentation.addmember

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.raza.householdrecharge.presentation.components.AppDatePicker
import com.raza.householdrecharge.presentation.components.AppSnackbar
import com.raza.householdrecharge.presentation.components.TitleBar
import com.raza.householdrecharge.presentation.snackbar.SnackbarUtil
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme
import kotlinx.coroutines.launch

@Composable
fun AddMemberScreen(
    viewModel: AddMemberViewModel = hiltViewModel(),
    onSuccess: () -> Unit = {},
    onFailure: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar("Add Household Member")
        },

        snackbarHost = {
            AppSnackbar(hostState = snackbarHostState)
        },

        ) { paddingValues ->

        val modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = 10.dp,
                bottom = 10.dp
            )


        val scrollState = rememberScrollState()

        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = modifier,
                label = {
                    Text("Name")
                },
                onValueChange = {
                    viewModel.accountName = it
                },
                value = viewModel.accountName
            )

            Spacer(modifier = Modifier.padding(12.dp))

            OutlinedTextField(
                modifier = modifier,
                label = {
                    Text("Mobile Number")
                },
                onValueChange = {
                    viewModel.mobileNumber = it
                },
                value = viewModel.mobileNumber
            )

            AppDatePicker(
                modifier = modifier,

                label = "Last Recharge Date",

                onDateSelected = {
                    viewModel.lastRechargeDate = it.toString()
                },

                value = viewModel.lastRechargeDate,
            )

            OutlinedTextField(
                modifier = modifier,
                label = {
                    Text("Plan Amount")
                },
                onValueChange = {
                    viewModel.planAmount = it
                },
                value = viewModel.planAmount
            )

            AppDatePicker(
                modifier = modifier,

                label = "Plan Expiry Date",

                onDateSelected = {
                    viewModel.planExpiryDate = it.toString()
                },

                value = viewModel.planExpiryDate,
            )

            OutlinedTextField(
                modifier = modifier,
                label = {
                    Text("Plan Duration Days")
                },
                onValueChange = {
                    viewModel.planDurationDays = it
                },
                value = viewModel.planDurationDays
            )

            OutlinedTextField(
                modifier = modifier,
                label = {
                    Text("Days to Expiry")
                },
                onValueChange = {
                    viewModel.daysToExpiry = it
                },
                value = viewModel.daysToExpiry
            )

            Spacer(modifier = Modifier.padding(20.dp))

            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                )
            }

            OutlinedButton(
                modifier = modifier,

                onClick = {
                    viewModel.onAddMember(
                        onSuccess = { memberId ->

                            scope.launch {
                                SnackbarUtil.show(
                                    snackbarHostState = snackbarHostState,
                                    message = "member added $memberId"
                                )
                            }

                            onSuccess()
                        },

                        onFailure = { errorMessage ->

                            scope.launch {
                                SnackbarUtil.show(
                                    snackbarHostState = snackbarHostState,
                                    message = "error: $errorMessage"
                                )
                            }

                            onFailure()
                        }
                    )
                }
            ) {
                Text("Add Member")
            }
        }
    }
}

@Composable
fun AddMemberContent() {
    HouseholdRechargeTheme(dynamicColor = false) {
        AddMemberScreen()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun AddMemberScreenDarkPreview() {
    AddMemberContent()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun AddMemberScreenLightPreview() {
    AddMemberContent()
}