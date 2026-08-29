package com.raza.householdrecharge.v1.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raza.householdrecharge.R
import com.raza.householdrecharge.v1.data.RechargeRequestEntity
import com.raza.householdrecharge.v1.data.RechargeRequestStatus

@Composable
fun RequestHistoryScreen(
    requests: List<RechargeRequestEntity>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.history),
            style = MaterialTheme.typography.headlineMedium
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = requests, key = { it.id }) { request ->
                RequestHistoryCard(request)
            }
        }
    }
}

@Composable
private fun RequestHistoryCard(request: RechargeRequestEntity) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.requested_on,
                    formatDate(request.requestedAt)
                )
            )

            if (request.status == RechargeRequestStatus.COMPLETED) {
                request.completedAt?.let {
                    Text(text = stringResource(R.string.completed_on, formatDate(it)))
                }
            }
        }
    }
}