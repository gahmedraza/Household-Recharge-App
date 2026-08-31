package com.raza.householdrecharge.v2.invitation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.v2.common.LargeHeadlineText
import com.raza.householdrecharge.v2.common.LargeTitleText
import com.raza.householdrecharge.v2.repository.cleanString

@Composable
fun ConfirmInvitationScreen(viewModel: InvitationViewModel) {
    ConfirmInvitationBody(viewModel = viewModel)
}

@Composable
fun ConfirmInvitationBody(viewModel: InvitationViewModel) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(

        ) {

            LargeHeadlineText(
                text = "Join Household"
            )

            LargeTitleText(
                text = "You'are invited to join"
            )

            LargeTitleText(
                text = "Teegars"
            )

            LargeTitleText(
                text= "Do you want to join this household?"
            )

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
fun ConfirmInvitationDarkPreview() {
    ConfirmInvitationBody(viewModel = viewModel())
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun ConfirmInvitationLightPreview() {
    ConfirmInvitationBody(viewModel = viewModel())
}