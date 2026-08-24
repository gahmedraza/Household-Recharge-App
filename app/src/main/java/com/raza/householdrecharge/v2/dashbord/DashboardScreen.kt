package com.raza.householdrecharge.v2.dashbord

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.data.Member
import com.raza.householdrecharge.v2.TitleBar
import com.raza.householdrecharge.v2.common.MemberDto

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onClick: (String, String) -> Unit,
    onAddMember: () -> Unit,
    onDashboardClick: () -> Unit,
    onRechargeHistoryClick: () -> Unit,
    onSettingClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.loadMembers(
            onSuccess = {

                Log.d("TAG", "dashboard items fetched from firebase")
            },

            onFailure = {

                Log.e("TAG", "dashboard items fetch resulted in error")
                Log.e("TAG", "error: $it")
            }
        )
    }

    DashboardContent(
        viewModel = viewModel,
        onClick = { memberId, mobileNumber ->
            onClick(memberId, mobileNumber)
        },
        onAddMember = {
            onAddMember()
        },
        onDashboardClick = {
            onDashboardClick()
        },
        onRechargeHistoryClick = {
            onRechargeHistoryClick()
        },
        onSettingClick = {
            onSettingClick()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
    viewModel: DashboardViewModel,
    onClick: (String, String) -> Unit,
    onAddMember: () -> Unit,
    onDashboardClick: () -> Unit,
    onRechargeHistoryClick: () -> Unit,
    onSettingClick: () -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

        topBar = {
            TitleBar("Dashboard")
        },

        floatingActionButton = {
            if (true) {
                FloatingActionButton(
                    onClick = {
                        onAddMember()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "add member"
                    )
                }
            }
        },

        bottomBar = {
            NavigationBar{
                NavigationBarItem(
                    selected = true,
                    onClick = {
                        onDashboardClick()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Dashboard"
                        )
                    },
                    label = {
                        Text("Dashboard")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        onRechargeHistoryClick()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "Recharge History"
                        )
                    },
                    label = {
                        Text("History")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        onSettingClick()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Setting"
                        )
                    },
                    label = {
                        Text("Setting")
                    }
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
                if (viewModel.members.isEmpty()) {
                    item {
                        Text(
                            text = "No items available",
                            style = MaterialTheme.typography.displayLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    items(viewModel.members) { item ->
                        DashboardListItem(
                            item = item,
                            onClick = {
                                onClick(item.id, item.mobileNumber)
                            }
                        )
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
    DashboardScreen(
        viewModel = viewModel(),
        onClick = { memberId, mobileNumber ->

        },
        onAddMember = {

        },
        onDashboardClick = {

        },
        onRechargeHistoryClick = {

        },
        onSettingClick = {

        }
    )
}

@Composable
fun DashboardListItem(
    item: Member,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .clickable {
                onClick()
            },
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
                text = "Mobile: ${item.mobileNumber}",
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
