package com.raza.householdrecharge.ui.invitation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raza.householdrecharge.common.LargeDisplayText
import com.raza.householdrecharge.common.LargeHeadlineText
import com.raza.householdrecharge.common.LargeTitleText
import com.raza.householdrecharge.common.getViewModel
import com.raza.householdrecharge.ui.setuphousehold.HouseholdViewModel
import com.raza.householdrecharge.util.cleanString

@Composable
fun ConfirmHouseholdScreen(viewModel: HouseholdViewModel) {
    ConfirmHouseholdBody(viewModel = viewModel)
}

@Composable
fun ConfirmHouseholdBody(viewModel: HouseholdViewModel) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 40.dp,
                bottom = 40.dp
            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            LargeHeadlineText(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = "Join Household",
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(100.dp))

            LargeTitleText(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = "You are invited to join",
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(60.dp))

            LargeDisplayText(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = "Teegars"
            )

            Spacer(Modifier.height(60.dp))

            LargeTitleText(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = "Do you want to join this household?",
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(200.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                OutlinedButton(
                    onClick = {

                    }
                ) {
                    LargeTitleText(
                        text = "Cancel"
                    )
                }

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                OutlinedButton(
                    onClick = {

                        viewModel.onJoinHousehold(
                            viewModel.invitationCode,
                            viewModel.household.id.cleanString(),
                            onSuccess = {

                            },
                            onFailure = {

                            }
                        )
                    }
                ) {
                    LargeTitleText(
                        text = "Join"
                    )
                }
            }

        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ConfirmHouseholdDarkPreview() {
    ConfirmHouseholdContent()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun ConfirmHouseholdLightPreview() {
    ConfirmHouseholdContent()
}

@Composable
fun ConfirmHouseholdContent() {
    val viewModel =
        getViewModel(HouseholdViewModel::class.java)

    ConfirmHouseholdBody(viewModel = viewModel)
}