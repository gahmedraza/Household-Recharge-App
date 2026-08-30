package com.raza.householdrecharge.v2.invitation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.v2.common.LargeBodyText
import com.raza.householdrecharge.v2.common.LargeTitleText
import com.raza.householdrecharge.v2.common.TitleBar

@Composable
fun AddInvitationScreen(
    viewModel: InvitationViewModel
) {

    AddInvitationBody(
        modifier = Modifier
            .fillMaxSize(),
        viewModel = viewModel
    )
}

@Composable
fun AddInvitationBody(
    modifier: Modifier,
    viewModel: InvitationViewModel
) {

    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        elevation = CardDefaults
            .cardElevation(
                defaultElevation = 4.dp
            ),
        shape = RoundedCornerShape(4.dp)
    ) {
        var status by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 20.dp,
                        bottom = 20.dp
                    )
            ) {
                LargeTitleText(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    text = "Create Invitation"
                )

                Spacer(
                    modifier = Modifier
                        .height(100.dp)
                )

                LargeBodyText(
                    text = "Generate a one-time invitation code for a household member."
                )

                Spacer(
                    modifier = Modifier
                        .height(50.dp)
                )

                LargeBodyText(
                    text = "The code will expire after 24 hours."
                )

                Spacer(
                    modifier = Modifier
                        .height(100.dp)
                )

                LargeBodyText(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    text = "invitation code: ${viewModel.invitationCode}"
                )

                Spacer(
                    modifier = Modifier
                        .height(100.dp)
                )

                if (viewModel.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                if(!viewModel.isLoading) {
                    LargeBodyText(
                        text = status,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally))
                }

                Spacer(
                    modifier = Modifier
                        .height(100.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    OutlinedButton(
                        onClick = {

                        }
                    ) {

                        Text("Cancel")
                    }

                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                    )

                    OutlinedButton(
                        enabled = !viewModel.isLoading,

                        onClick = {

                            viewModel.invitationCode = viewModel.generateInvitationCode()

                            viewModel.createInvitation(
                                code = viewModel.invitationCode,
                                onSuccess = { data ->

                                    status = data
                                },
                                onFailure = { error ->

                                    status = error
                                }
                            )
                        }
                    ) {

                        Text("Create")
                    }
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
fun AddInvitationDarkPreview() {
    AddInvitationBody(
        modifier = Modifier
            .fillMaxWidth(),
        viewModel = viewModel()
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun AddInvitationLightPreview() {
    AddInvitationBody(
        modifier = Modifier
            .fillMaxWidth(),
        viewModel = viewModel()
    )
}