package com.raza.householdrecharge.ui.recharge

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.toString
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raza.householdrecharge.common.RechargeStatusIndicator
import com.raza.householdrecharge.common.TitleBar
import com.raza.householdrecharge.common.getPrintableDate
import com.raza.householdrecharge.common.getViewModel
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto
import com.raza.householdrecharge.data.remote.dto.RechargeDto
import com.raza.householdrecharge.domain.model.Recharge

@Composable
fun RechargeListingScreen(
    memberId: String,
    mobileNumber: String,
    viewModel: RechargeListingViewModel,
    onSuccess: () -> Unit,
    onFailure: () -> Unit,
    onAddRecharge: (String, String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.loadAllRecharges(
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
            TitleBar("Recharge Listing")
        },

        /*floatingActionButton = {
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
        }*/
    ) { paddingValues ->

        Body(viewModel, Modifier.padding(paddingValues))
    }
}

@Composable
fun Body(
    viewModel: RechargeListingViewModel,
    modifier: Modifier
) {
    if (viewModel.rechargeList.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "no recharges found",
                style = MaterialTheme.typography.titleLarge
            )
        }
    } else {
        LazyColumn(modifier = modifier) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

            items(viewModel.rechargeList) { item ->
                RechargeListItemCard2(
                    item = item,
                    onClick = {

                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RechargeListingScreenDarkPreview() {
    Content(
        item = null
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun RechargeListingScreenLightPreview() {
    Content(
        item = null
    )
}

@Composable
fun Content(item: Recharge?) {
    val viewModel =
        getViewModel(RechargeListingViewModel::class.java)

    RechargeListingScreen(
        memberId = "",
        mobileNumber = "",
        viewModel = viewModel,
        onSuccess = {},
        onFailure = {},
        onAddRecharge = { memberId, mobileNumber ->

        }
    )
}

@Composable
fun RechargeListItemCard(
    item: RechargeDto,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 6.dp
            )
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 1.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Recharge",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "₹${item.rechargeAmount}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = getPrintableDate(
                        item.rechargeDate.toString()
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "${item.rechargedBy}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "View Recharge",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// recharge list item new card
@Composable
fun RechargeListItemCard2(
    item: RechargeDto,
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

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = "${item.mobileNumber}",
                    letterSpacing = 1.sp,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "2GB per day for 84 days".uppercase(),
                    letterSpacing = 2.sp,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "Recharge Amount :",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "  ${item.rechargeAmount}",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = " INR",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "Recharge Date :",
                    style = MaterialTheme.typography.bodyLarge,
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = " ${getPrintableDate(item.rechargeDate.toString())}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "Expiry Date :",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = " ${getPrintableDate(item.expiryDate.toString())}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(10.dp))


            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "Recharged by :",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = " ${item.rechargedBy}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
//