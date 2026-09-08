package com.raza.householdrecharge.presentation.invitation

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.presentation.components.LargeBodyText
import com.raza.householdrecharge.presentation.components.LargeDisplayText
import com.raza.householdrecharge.presentation.components.LargeHeadlineText
import com.raza.householdrecharge.presentation.components.LargeTitleText

@Composable
fun AddInvitationScreen(
    viewModel: InvitationViewModel = hiltViewModel(),
    onInvitationVerified: (String) -> Unit = { a-> }
) {

    AddInvitationBody(
        modifier = Modifier
            .fillMaxSize(),
        viewModel = viewModel
    )
}

@Composable
fun AddInvitationBody(
    modifier: Modifier = Modifier.fillMaxWidth(),
    viewModel: InvitationViewModel = hiltViewModel()
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
                        start = 30.dp,
                        end = 30.dp,
                        top = 20.dp,
                        bottom = 20.dp
                    )
            ) {
                LargeHeadlineText(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    text = "Create Invitation"
                )

                Spacer(
                    modifier = Modifier
                        .height(50.dp)
                )

                LargeTitleText(
                    text = "Generate a one-time invitation code for a household member.",
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier
                        .height(50.dp)
                )

                LargeTitleText(
                    text = "The code will expire after 24 hours.",
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier
                        .height(50.dp)
                )

                Row {

                    LargeTitleText(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = "invitation code:",
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(Modifier.weight(1f))

                    LargeDisplayText(
                        modifier = Modifier.padding(
                            end = 20.dp
                        ),
                        text = viewModel.invitationCode
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(50.dp)
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
                            .align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(50.dp)
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
    AddInvitationBody()
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