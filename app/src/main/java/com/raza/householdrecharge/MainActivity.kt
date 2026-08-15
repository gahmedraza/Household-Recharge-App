package com.raza.householdrecharge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.data.MemberEntity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                HouseHoldScreen()
            }
        }
    }
}

@Composable
fun HouseHoldScreen() {
    val application = LocalContext.current.applicationContext
            as HouseholdRechargeApplication

    val viewModel: MemberViewModel = viewModel(
        factory = MemberViewModel.Factory(application.repository)
    )

    val members by viewModel.members.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "My Household",
            style = MaterialTheme.typography.headlineMedium
        )

        LazyColumn(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = members,
                key = { member -> member.id }
            ) { member ->
                MemberCard(
                    member = member,
                    onRequestRecharge = {
                        viewModel.requestRecharge(member.id)
                    },
                    onRechargeDone = {
                        viewModel.markRechargeDone(
                            member.id,
                            member.planDurationDays
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun MemberCard(
    member: MemberEntity,
    onRequestRecharge: () -> Unit,
    onRechargeDone: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = member.name,
                style = MaterialTheme.typography.titleLarge
            )

            Text(text = member.mobileNumber)

            if(member.rechargeRequested) {
                Text(
                    text = "Recharge requested",
                    color = MaterialTheme.colorScheme.error
                )

                Button(
                    onClick = onRechargeDone,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Mark Recharge Done")
                }
            } else {
                Button(
                    onClick = onRequestRecharge,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Request Recharge")
                }
            }
        }
    }
}