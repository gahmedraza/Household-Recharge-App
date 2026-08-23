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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raza.householdrecharge.v2.TitleBar

@Composable
fun SettingScreen(
    onAddMember: () -> Unit,
    onAddHousehold: () -> Unit
) {

    Scaffold(
        topBar = {
            TitleBar("Settings")
        }
    ) { paddingValues ->

        Body(
            modifier = Modifier.padding(paddingValues),
            onAddMember = {
                onAddMember()
            },
            onAddHousehold = {
                onAddHousehold()
            }
        )
    }
}

@Composable
fun Body(
    modifier: Modifier,
    onAddMember: () -> Unit,
    onAddHousehold: () -> Unit
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
            text = "Other",
            modifier = cellModifier,
            style = MaterialTheme.typography.titleLarge
        )

        HorizontalDivider(modifier = Modifier.height(1.dp))
    }
}

@Composable
fun Content() {
    SettingScreen(
        onAddMember = {},
        onAddHousehold = {}
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