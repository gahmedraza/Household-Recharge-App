package com.raza.householdrecharge.v2.rechargehistory

import android.content.res.Configuration
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.v2.TitleBar
import com.raza.householdrecharge.v2.common.getPrintableDate

@Composable
fun RechargeHistoryScreen(
    memberId: String,
    mobileNumber: String,
    viewModel: RechargeHistoryViewModel,
    onSuccess: () -> Unit,
    onFailure: () -> Unit,
    onAddRecharge: (String, String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.loadRechargeHistory(
            memberId = memberId,
            mobileNumber = mobileNumber,

            onSuccess = {

            },
            onFailure = {

            }
        )
    }

    Scaffold(
        topBar = {
            TitleBar("Recharge History")
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddRecharge(
                        memberId, mobileNumber
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add recharge"
                )
            }
        }
    ) { paddingValues ->

        Body(viewModel, Modifier.padding(paddingValues))
    }
}

@Composable
fun Body(
    viewModel: RechargeHistoryViewModel,
    modifier: Modifier
) {
    if (viewModel.mobileRechargeHistory.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "no recharges found",
                style = MaterialTheme.typography.displayLarge
            )
        }
    } else {
        LazyColumn(modifier = modifier) {
            items(viewModel.mobileRechargeHistory) { item ->
                RechargeHistoryCard(
                    item = item,
                    onClick = {

                    }
                )
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RechargeHistoryScreenDarkPreview() {
    Content(
        item = null
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun RechargeHistoryScreenLightPreview() {
    Content(
        item = null
    )
}

@Composable
fun Content(item: RechargeHistory?) {
    RechargeHistoryScreen(
        memberId = "",
        mobileNumber = "",
        viewModel = viewModel(),
        onSuccess = {},
        onFailure = {},
        onAddRecharge = { memberId, mobileNumber ->

        }
    )
}

@Composable
fun RechargeHistoryCard(
    item: RechargeHistory,
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
                text = "Amount: ${item.amount}",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Date: ${getPrintableDate(item.date)}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Recharge By: ${item.rechargedBy}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}