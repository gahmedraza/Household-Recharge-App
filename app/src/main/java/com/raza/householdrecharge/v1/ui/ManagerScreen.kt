package com.raza.householdrecharge.v1.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raza.householdrecharge.v1.MemberViewModel
import com.raza.householdrecharge.R
import com.raza.householdrecharge.v1.data.UserRole

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagerScreen(
    viewModel: MemberViewModel,
    onHistory: (Long) -> Unit
) {

    var showAddMember by remember {
        mutableStateOf(false)
    }

    val members by viewModel.members.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.household))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddMember = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add)
                )
            }
        }
    ) { paddingValues ->

        if (showAddMember) {

            AddMemberScreen(
                onAdd = { name, mobileNumber, planDurationDays ->

                    viewModel.addMember(
                        name = name,
                        mobileNumber = mobileNumber,
                        planDurationDays = planDurationDays
                    )

                    showAddMember = false
                },

                onCancel = {

                    showAddMember = false
                }
            )
        } else {

            if(members.isEmpty()) {
                Box(Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Text(text = stringResource(R.string.no_members),
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(text = stringResource(R.string.add_first_member),
                            style = MaterialTheme.typography.bodyMedium)
                    }
                }

            } else {
                MemberList(
                    members = members,
                    userRole = UserRole.MANAGER,
                    viewModel = viewModel,
                    onHistory = onHistory,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}