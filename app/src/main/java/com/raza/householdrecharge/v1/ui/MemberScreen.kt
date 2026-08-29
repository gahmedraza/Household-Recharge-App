package com.raza.householdrecharge.v1.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raza.householdrecharge.v1.MemberViewModel
import com.raza.householdrecharge.R
import com.raza.householdrecharge.v1.data.MemberEntity
import com.raza.householdrecharge.v1.data.UserRole
import com.raza.householdrecharge.v1.notification.RechargeNotification
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberScreen(
    viewModel: MemberViewModel,
    onHistory: (Long) -> Unit
) {

    val members by viewModel.members.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.household))
                }
            )
        }
    ) { paddingValues ->

        if(members.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                //contentAlignment = Alignment.Center TODO
            ) {

                Text(
                    text = stringResource(R.string.no_members)
                )
            }
        } else {

            MemberList(
                members = members,
                userRole = UserRole.MEMBER,
                viewModel = viewModel,
                onHistory = onHistory,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun MemberList(
    members: List<MemberEntity>,
    userRole: UserRole,
    viewModel: MemberViewModel,
    onHistory: (Long) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(
            items = members,
            key = { it.id }
        ) {
            member ->

            val activeRequest by viewModel
                .observeActiveRequest(member.id)
                .collectAsState()

            MemberCard(
                member = member,
                userRole = userRole,
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
                        id = member.id,
                        planDurationDays = member.planDurationDays,
                        requestId = activeRequest?.id
                    )
                },

                onHistory = {
                    onHistory(member.id)
                }
            )
        }
    }
}