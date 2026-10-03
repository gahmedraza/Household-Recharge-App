package com.raza.householdrecharge.presentation.dashbord

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.domain.model.Recharge
import com.raza.householdrecharge.presentation.components.TitleBar
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewmodel: DashboardViewModel = hiltViewModel(),
    onAddRecharge: (String, String) -> Unit = { a, b -> },
    onAddMobileNumber: () -> Unit = {}
) {

    val dashboardUIState by viewmodel.dashboardUIState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewmodel.getAllMobileNumbers()
        viewmodel.getAllRecharges()
    }

    Scaffold(
        topBar = {
            TitleBar("Dashboard")
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddMobileNumber()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add mobile number"
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

                if(dashboardUIState.isLoading) {

                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Center),
                        )
                    }
                }

                else if (dashboardUIState.mobileNumberList.isEmpty()) {
                    item {
                        Text(
                            text = "no mobile numbers found",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                else {
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    items(dashboardUIState.mobileNumberList) { item ->

                        Logger.log(dashboardUIState.rechargeList.toString())
                        var rechargeDto = dashboardUIState.rechargeList.find {
                            Logger.log("rechargeId: ${it.id}, lastRechargeId: ${item.lastRechargeId}")
                            it.id == item.lastRechargeId
                        }
                        Logger.log(rechargeDto.toString())

                        if(rechargeDto == null) {
                            Logger.log("empty recharge dto")
                            rechargeDto = Recharge()
                        }

                        DashboardListItemCard(
                            item = item,
                            recharge = rechargeDto,
                            onClick = {
                                onAddRecharge(item.mobileNumber.toString(), item.id)
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }

        if(dashboardUIState.showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    viewmodel.onShowBottomSheetModified(false)
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("api response")

                    Text(dashboardUIState.apiResponse)
                }
            }
        }
    }
}

@Composable
fun DashboardContent() {
    HouseholdRechargeTheme(dynamicColor = false) {
        DashboardScreen()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DashboardScreenDarkPreview() {
    DashboardContent()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DashboardScreenLightPreview() {
    DashboardContent()
}