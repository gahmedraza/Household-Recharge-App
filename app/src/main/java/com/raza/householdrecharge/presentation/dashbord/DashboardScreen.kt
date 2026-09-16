package com.raza.householdrecharge.presentation.dashbord

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.raza.householdrecharge.core.logging.log
import com.raza.householdrecharge.presentation.components.RechargeStatusIndicator
import com.raza.householdrecharge.presentation.components.TitleBar
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto
import com.raza.householdrecharge.data.remote.dto.RechargeDto
import com.raza.householdrecharge.presentation.components.getPrintableDate
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme

const val TAG2 = "Dashboard"

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onDashboardCardClick: (String, String) -> Unit = { a,b -> },
    onAddMobileNumber: () -> Unit = {}
) {

    val mobileNumberList by viewModel.mobileNumberList.collectAsStateWithLifecycle()
    val rechargeList by viewModel.rechargeList.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAllMobileNumbers()
        viewModel.getAllRecharges()
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

                if(viewModel.isLoading) {

                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Center),
                        )
                    }
                }

                else if (mobileNumberList.isEmpty()) {
                    item {
                        Text(
                            text = "No mobile numbers added",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                else {
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    items(mobileNumberList) { item ->

                        log(TAG2, rechargeList.toString())
                        var rechargeDto = rechargeList.find {
                            log(TAG2, "rechargeId: ${it.id}, lastRechargeId: ${item.lastRechargeId}")
                            it.id == item.lastRechargeId
                        }
                        log(TAG2, rechargeDto.toString())

                        if(rechargeDto == null) {
                            log(TAG2, "empty recharge dto")
                            rechargeDto = RechargeDto()
                        }

                        DashboardListItemCard(
                            item = item,
                            recharge = rechargeDto,
                            onClick = {
                                onDashboardCardClick(item.mobileNumber.toString(), item.id)
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardListItemCard(
    item: MobileNumberDto,
    recharge: RechargeDto,
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
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp)
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Icon(
                    imageVector = Icons.Filled.PhoneAndroid,
                    contentDescription = "Mobile Number",
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = "${item.mobileNumber}",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Mobile Number",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                //text = "Last Recharge: 979 INR",
                text = "Last Recharge: ${recharge.rechargeAmount}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                //text = "2GB per day for 84 days",
                text = recharge.rechargeDescription,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                //text = "Recharge Date: 02 Sep 2026",
                text = "Recharge Date: ${getPrintableDate(recharge.rechargeDate.toString())}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                //text = "Recharged by: Raza",
                text = "Recharged by: ${recharge.rechargedBy}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Column {
                    Row {
                        RechargeStatusIndicator(
                            modifier = Modifier.align(
                                alignment = Alignment.CenterVertically
                            )
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "Plan Active",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Expires in 24 days",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                OutlinedButton(
                    modifier = Modifier
                        .align(Alignment.Top)
                        .height(40.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(
                        top = 0.dp,
                        bottom = 0.dp,
                        start = 24.dp,
                        end = 24.dp
                    ),
                    onClick = {

                    }
                ) {
                    Text(
                        text = "Request Recharge",
                        style = MaterialTheme.typography.bodyLarge
                    )
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