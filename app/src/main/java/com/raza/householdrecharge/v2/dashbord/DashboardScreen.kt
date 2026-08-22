package com.raza.householdrecharge.v2.dashbord

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    LazyColumn {
        items(viewModel.items) { item ->
            DashboardListItem(item)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DashboardScreenDarkPreview() {
    content()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DashboardScreenLightPreview() {
    content()
}

@Composable
fun content() {
    DashboardScreen(viewModel = viewModel())
}

@Composable
fun DashboardListItem(item: DashboardItem) {
    Column() {
        Text("Number: ${item.number}")
        Text("Expiry: ${item.expiry}")
        Text("Last Recharge: ${item.lastRecharge}")
    }
}

data class DashboardItem(
    val number: String,
    val expiry: String,
    val lastRecharge: String
)