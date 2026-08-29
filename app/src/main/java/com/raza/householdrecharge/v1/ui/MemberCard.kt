package com.raza.householdrecharge.v1.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raza.householdrecharge.R
import com.raza.householdrecharge.v1.data.MemberEntity
import com.raza.householdrecharge.v1.data.PlanStatus
import com.raza.householdrecharge.v1.data.RechargeRequestEntity
import com.raza.householdrecharge.v1.data.UserRole
import com.raza.householdrecharge.v1.util.calculateMemberStatus
import com.raza.householdrecharge.v1.util.formatDate

@Composable
fun MemberCard(
    member: MemberEntity,
    userRole: UserRole,
    activeRequest: RechargeRequestEntity?,
    onRequestRecharge: () -> Unit,
    onRechargeDone: () -> Unit,
    onHistory: () -> Unit
) {

    val memberStatus = calculateMemberStatus(
        member.planExpiryDate
    )

    val status = memberStatus.planStatus

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

            Text(text = member.mobileNumber,
                style = MaterialTheme.typography.bodyMedium)

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            when (status) {

                PlanStatus.ACTIVE -> {

                    Text(
                        text = stringResource(
                                R.string.days_remaining,
                                memberStatus.daysRemaining
                            ),
                        style = MaterialTheme.typography.titleMedium
                    )

                    member.planExpiryDate?.let {
                        Text(
                            text = stringResource(
                                R.string.expires,
                                formatDate(it)
                            )
                        )
                    }
                }

                PlanStatus.DUE -> {

                    Text(
                        text = stringResource(R.string.today),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                PlanStatus.EXPIRED -> {

                    Text(
                        text = stringResource(
                                R.string.days_overdue,
                                -memberStatus.daysRemaining
                            ),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            member.lastRechargeDate?.let {
                Text(
                    text = stringResource(
                        R.string.recharged,
                        formatDate(it)
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            when (userRole) {
                UserRole.MANAGER -> {

                    if(activeRequest != null) {
                        Text(
                            text = stringResource(
                                R.string.requested_on,
                                formatDate(activeRequest.requestedAt)
                            ),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Button(
                        onClick = onRechargeDone,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.done))
                    }
                }

                UserRole.MEMBER -> {

                    when {

                        activeRequest != null -> {

                            Text(
                                text = stringResource(R.string.request_pending),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        status != PlanStatus.ACTIVE -> {
                            Button(
                                onClick = onRequestRecharge,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    stringResource(R.string.request)
                                )
                            }
                        }
                    }
                }
            }

            OutlinedButton(
                onClick = onHistory,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.history)
                )
            }
        }
    }
}