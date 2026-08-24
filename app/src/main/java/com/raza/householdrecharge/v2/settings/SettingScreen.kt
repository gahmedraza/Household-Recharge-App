package com.raza.householdrecharge.v2.settings

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.v2.TitleBar

@Composable
fun SettingScreen(
    viewModel: SettingViewModel,
    onAddMember: () -> Unit,
    onAddHousehold: () -> Unit,
    onSignOut: () -> Unit
) {

    Scaffold(
        topBar = {
            TitleBar("Settings")
        }
    ) { paddingValues ->

        Body(
            viewModel = viewModel,
            modifier = Modifier.padding(paddingValues),
            onAddMember = {
                onAddMember()
            },
            onAddHousehold = {
                onAddHousehold()
            },
            onSignOut = onSignOut
        )
    }
}

@Composable
fun Body(
    viewModel: SettingViewModel,
    modifier: Modifier,
    onAddMember: () -> Unit,
    onAddHousehold: () -> Unit,
    onSignOut: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var household by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getName(
            onSuccess = { userName ->
                name = userName
            },
            onFailure = {

            }
        )
        viewModel.getHouseholdName(
            onSuccess = { householdName ->
                household = householdName
            },
            onFailure = {

            }
        )
        viewModel.getMobileNumber(
            onSuccess = { phoneNumber ->
                mobileNumber = phoneNumber
            },
            onFailure = {

            }
        )
    }

    Column(modifier = modifier) {
        val cellPadding = PaddingValues(
            start = 30.dp,
            end = 30.dp,
            top = 20.dp,
            bottom = 20.dp
        )

        val cellModifier = Modifier
            .fillMaxWidth()
            .padding(cellPadding)

        HorizontalDivider(modifier = Modifier.height(1.dp))

        //
        Text(
            text = "Name: $name",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    //onAddHousehold()
                }
                .padding(cellPadding),

            style = MaterialTheme.typography.titleLarge
        )

        HorizontalDivider(modifier = Modifier.height(1.dp))

        Text(
            text = "Mobile Number: $mobileNumber",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    //onAddMember()
                }
                .padding(cellPadding),

            style = MaterialTheme.typography.titleLarge
        )

        HorizontalDivider(modifier = Modifier.height(1.dp))

        Text(
            text = "Role",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    //onAddMember()
                }
                .padding(cellPadding),

            style = MaterialTheme.typography.titleLarge
        )

        HorizontalDivider(modifier = Modifier.height(1.dp))
        //

        Text(
            text = "Household: $household",
            modifier = cellModifier,
            style = MaterialTheme.typography.titleLarge
        )

        HorizontalDivider(modifier = Modifier.height(1.dp))

        Text(
            text = "Admin",
            modifier = cellModifier,
            style = MaterialTheme.typography.titleLarge
        )

        HorizontalDivider(modifier = Modifier.height(1.dp))

        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(
                text = "Add Household",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onAddHousehold()
                    }
                    .padding(cellPadding),

                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider(modifier = Modifier.height(1.dp))

            Text(
                text = "Add Member",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onAddMember()
                    }
                    .padding(cellPadding),

                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider(modifier = Modifier.height(1.dp))
        }

        Text(
            text = "SignOut",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.signOut(
                        onSuccess = {
                            onSignOut()
                        },
                        onFailure = {

                        }
                    )
                }
                .padding(cellPadding),
            style = MaterialTheme.typography.titleLarge
        )

        HorizontalDivider(modifier = Modifier.height(1.dp))
    }
}

@Composable
fun Content() {
    SettingScreen(
        viewModel = viewModel(),
        onAddMember = {},
        onAddHousehold = {},
        onSignOut = {}
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingScreenDarkPreview() {
    Content()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SettingScreenLightPreview() {
    Content()
}