package com.raza.householdrecharge.presentation.dashbord

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raza.householdrecharge.domain.model.MobileNumber
import com.raza.householdrecharge.domain.model.Recharge
import com.raza.householdrecharge.presentation.components.ActivePlanIndicator
import com.raza.householdrecharge.presentation.components.ExpiredPlanIndicator
import com.raza.householdrecharge.presentation.components.getPrintableDate
import com.raza.householdrecharge.presentation.theme.Red66

@Composable
fun DashboardListItemCard(
    item: MobileNumber,
    recharge: Recharge,
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
                    val millisPerDay = 24*60*60*1000L
                    val currentMillis = System.currentTimeMillis()
                    val expiryMillis = recharge.expiryDate
                    val daysToExpiry = (expiryMillis - currentMillis + millisPerDay - 1) / millisPerDay
                    var isActive: Boolean
                    var activeText: String
                    var expiryInfoText: String

                    if(daysToExpiry < 0) {
                        //expired
                        isActive = false
                        activeText = "Plan Expired"
                        expiryInfoText = "Expired on ${getPrintableDate(recharge.expiryDate.toString())}"
                    } else {

                        isActive = true
                        activeText = "Plan Active"
                        expiryInfoText = "Expires in $daysToExpiry days"
                    }

                    Row {

                        if(isActive) {
                            ActivePlanIndicator(
                                modifier = Modifier.align(
                                    alignment = Alignment.CenterVertically
                                )
                            )

                        } else {
                            ExpiredPlanIndicator(
                                modifier = Modifier.align(
                                    alignment = Alignment.CenterVertically
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            //text = "Plan Active",
                            text = activeText,
                            color = if (isActive) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                Red66
                            },
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        //text = "Expires in 24 days",
                        text = expiryInfoText,
                        color = if (isActive) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Red66
                        },
                        style = MaterialTheme.typography.bodyMedium
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