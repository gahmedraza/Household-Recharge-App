package com.raza.householdrecharge.v2.settings

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.raza.householdrecharge.v2.TitleBar

@Composable
fun SettingScreen() {

    Scaffold(
        topBar = {
            TitleBar("Settings")
        }
    ) { paddingValues ->

        Body(Modifier.padding(paddingValues))
    }
}

@Composable
fun Body(modifier: Modifier) {

    Column(modifier = modifier.padding(20.dp)) {

        val cellModifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .padding(10.dp)

        Text(
            text = "Account",
            modifier = cellModifier,
            style = MaterialTheme.typography.titleLarge
        )

        HorizontalDivider(modifier = Modifier.height(1.dp))

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Household",
            modifier = cellModifier,
            style = MaterialTheme.typography.titleLarge
        )

        HorizontalDivider(modifier = Modifier.height(1.dp))

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(
                text = "Household Details",
                modifier = cellModifier,
                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider(modifier = Modifier.height(1.dp))

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Add Member",
                modifier = cellModifier,
                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider(modifier = Modifier.height(1.dp))

            Spacer(modifier = Modifier.height(20.dp))
        }

        Text(
            text = "Other",
            modifier = cellModifier,
            style = MaterialTheme.typography.titleLarge
        )

        HorizontalDivider(modifier = Modifier.height(1.dp))

        Spacer(modifier = Modifier.height(20.dp))
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