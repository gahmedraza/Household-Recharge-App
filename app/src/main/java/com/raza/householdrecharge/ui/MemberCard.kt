package com.raza.householdrecharge.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raza.householdrecharge.R
import com.raza.householdrecharge.data.MemberEntity
import com.raza.householdrecharge.data.PlanStatus
import com.raza.householdrecharge.data.UserRole
import com.raza.householdrecharge.util.calculateMemberStatus
import com.raza.householdrecharge.util.calculatePlanStatus
import com.raza.householdrecharge.util.formatDate

@Composable
fun MemberCard(
    member: MemberEntity,
    userRole: UserRole,
    onRequestRecharge: () -> Unit,
    onRechargeDone: () -> Unit
) {

    val memberStatus = calculateMemberStatus(
        member.planExpiryDate
    )

    val status = memberStatus.planStatus

    val statusText = when (status) {
        PlanStatus.ACTIVE -> stringResource(R.string.active)
        PlanStatus.DUE -> stringResource(R.string.due)
        PlanStatus.EXPIRED -> stringResource(R.string.expired)
    }

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

            when(status) {
                PlanStatus.ACTIVE -> {
                    Text(text =
                    stringResource(
                        R.string.days_remaining,
                        memberStatus.daysRemaining
                    ))
                }
                PlanStatus.DUE -> {
                    Text(text =
                    stringResource(R.string.today))
                }
                PlanStatus.EXPIRED -> {
                    Text(
                        text =
                            stringResource(
                                R.string.days_overdue,
                                -memberStatus.daysRemaining
                            )
                    )
                }
            }

            member.lastRechargeDate?.let {
                Text(
                    text = "${stringResource(R.string.recharged)}: ${formatDate(it)}"
                )
            }

            member.planExpiryDate?.let {
                Text(
                    text = "${stringResource(R.string.expires)} : ${formatDate(it)}"
                )
            }


            when (userRole) {
                UserRole.MANAGER -> {
                    if (member.rechargeRequested) {
                        Text(
                            text = stringResource(R.string.requested),
                            color = MaterialTheme.colorScheme.error
                        )

                        Button(
                            onClick = onRechargeDone,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.done))
                        }
                    }
                }

                UserRole.MEMBER -> {
                    if (calculatePlanStatus(member.planExpiryDate) != PlanStatus.ACTIVE) {
                        Button(
                            onClick = onRequestRecharge,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.request))
                        }
                    }
                }
            }

            /*when (userRole) {
                null -> {
                    RoleScreen(
                        onRoleSelected = viewModel::setUserRole
                    )
                }

                else -> {
                    HouseholdScreen(
                        viewModel = viewModel,
                        userRole = userRole
                    )
                }
            }*/
        }
    }
}