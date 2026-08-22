package com.raza.householdrecharge.v2.dashbord

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.data.Member

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    LaunchedEffect(Unit) {
        viewModel.loadMembers(
            onSuccess = {

                Log.d("TAG", "dashboard items fetched from firebase")
            },

            onFailure = {

                Log.e("TAG", "dashboard items fetch resulted in error")
            }
        )
    }

    DashboardContent(viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(viewModel: DashboardViewModel) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
            ) {
                TopAppBar(
                    title = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background),
                            text = "Household Members",
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                )
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onAddMember(
                        onSuccess = {

                        },

                        onFailure = {

                        }
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add member"
                )
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn {
                if (viewModel.items.isEmpty()) {
                    item {
                        Text(
                            text = "No items available",
                            style = MaterialTheme.typography.displayLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    items(viewModel.items) { item ->
                        DashboardListItem(item)
                    }
                }
            }
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
fun DashboardListItem(item: Member) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        elevation = CardDefaults
            .cardElevation(
                defaultElevation = 4.dp
            ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = "Number: ${item.mobileNumber}",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Plan duration: ${item.planDurationDays} days",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Last Recharge: ${item.lastRechargeDate ?: "Not available"}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
