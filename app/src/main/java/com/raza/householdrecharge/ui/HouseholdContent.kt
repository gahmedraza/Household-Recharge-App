package com.raza.householdrecharge.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.HouseholdRechargeApplication
import com.raza.householdrecharge.MemberViewModel
import com.raza.householdrecharge.R
import com.raza.householdrecharge.data.UserRole
import com.raza.householdrecharge.notification.RechargeNotification

@SuppressLint("MissingPermission")
@Composable
fun HouseHoldContent(
    viewModel: MemberViewModel,
    userRole: UserRole,
    onHistory: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val application = LocalContext.current.applicationContext
            as HouseholdRechargeApplication

    val viewModel: MemberViewModel = viewModel(
        factory = MemberViewModel.Factory(application.repository)
    )

    val members by viewModel.members.collectAsState()

    var showAddMember by remember { mutableStateOf(false) }

    val userRole by viewModel.userRole.collectAsState()

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = {
                viewModel.setUserRole(UserRole.MANAGER)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.manager))
        }

        Button(
            onClick = {
                viewModel.setUserRole(UserRole.MEMBER)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.member))
        }
    }

    if (showAddMember) {
        AddMemberScreen(
            onAdd = { name, mobileNumber, planDurationDays ->
                viewModel.addMember(
                    name = name, mobileNumber = mobileNumber, planDurationDays = planDurationDays
                )
                showAddMember = false
            },
            onCancel = {
                showAddMember = false
            }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.household),
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

                    val activeRequest by viewModel
                        .observeActiveRequest(member.id)
                        .collectAsState()

                    MemberCard(
                        member = member,
                        userRole = userRole!!,
                        activeRequest = activeRequest,
                        onRequestRecharge = {
                            viewModel.requestRecharge(member.id)

                            RechargeNotification.showRequest(
                                context = context,
                                memberName = member.name
                            )
                        },
                        onRechargeDone = {
                            viewModel.markRechargeDone(
                                member.id,
                                member.planDurationDays,
                                activeRequest?.id
                            )
                        },
                        onHistory = {
                            onHistory(member.id)
                        }
                    )
                }
            }
        }
    }
}