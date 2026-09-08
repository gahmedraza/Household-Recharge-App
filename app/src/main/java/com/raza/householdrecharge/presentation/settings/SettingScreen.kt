package com.raza.householdrecharge.presentation.settings

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.raza.householdrecharge.presentation.components.TitleBar

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    onAddMember: () -> Unit = {},
    onAddHousehold: () -> Unit = {},
    onSignOut: () -> Unit = {},
    onInvitation: () -> Unit = {}
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
            onSignOut = onSignOut,
            onInvitation = onInvitation
        )
    }
}

@Composable
fun Body(
    viewModel: SettingViewModel,
    modifier: Modifier,
    onAddMember: () -> Unit,
    onAddHousehold: () -> Unit,
    onSignOut: () -> Unit,
    onInvitation: () -> Unit
) {

    Column(modifier = modifier) {
        val cellPadding = PaddingValues(
            start = 30.dp,
            end = 30.dp,
            top = 30.dp,
            bottom = 30.dp
        )

        val cellModifier = Modifier
            .fillMaxWidth()
            .padding(cellPadding)

        Text(
            text = "Account",
            modifier = cellModifier,
            style = MaterialTheme.typography.titleLarge
        )

        HorizontalDivider(modifier = Modifier.height(1.dp))

        Text(
            text = "Household",
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
                text = "Invitations",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onInvitation()
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
    SettingScreen()
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