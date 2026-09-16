package com.raza.householdrecharge.presentation.invitation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.R
import com.raza.householdrecharge.presentation.components.AppCard
import com.raza.householdrecharge.presentation.components.LargeBodyText
import com.raza.householdrecharge.presentation.components.LargeDisplayText
import com.raza.householdrecharge.presentation.theme.HouseholdRechargeTheme

@Composable
fun AddInvitationScreen(
    viewModel: InvitationViewModel = hiltViewModel(),
    onInvitationCreated: () -> Unit = {}
) {
    //
    var shouldProceed by rememberSaveable { mutableStateOf(false) }
    var status by rememberSaveable { mutableStateOf("") }

    AppCard {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                    )
            ) {
                /*SmallHeadlineText(
                    text = "Create Invitation",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                )

                Spacer(
                    modifier = Modifier
                        .height(50.dp)
                )*/

                LargeBodyText(
                    text = "Generate a one-time invitation code for a household member.",
                    //color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier
                        .height(50.dp)
                )

                LargeBodyText(
                    text = "The code will expire after 24 hours.",
                    //color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier
                        .height(50.dp)
                )

                LargeBodyText(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "invitation code:",
                    //color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier
                        .height(50.dp)
                )

                LargeDisplayText(
                    modifier = Modifier
                        .padding(
                            end = 20.dp
                        )
                        .align(Alignment.CenterHorizontally),
                    text = viewModel.invitationCode,
                    color = MaterialTheme.colorScheme.primary
                )

                /*Row {

                    LargeBodyText(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = "invitation code:",
                        //color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(Modifier.weight(1f))

                    LargeDisplayText(
                        modifier = Modifier.padding(
                            end = 20.dp
                        ),
                        text = viewModel.invitationCode,
                        color = MaterialTheme.colorScheme.primary
                    )
                }*/

                Spacer(
                    modifier = Modifier
                        .height(90.dp)
                )

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),

                    enabled = !shouldProceed,

                    onClick = {

                        viewModel.invitationCode = viewModel.generateInvitationCode()

                        viewModel.createInvitation(
                            code = viewModel.invitationCode,
                            onSuccess = { data ->

                                status = data
                                shouldProceed = true
                            },
                            onFailure = { error ->

                                status = error
                                shouldProceed = false
                            }
                        )
                    }
                ) {

                    Text("Create")
                }

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),

                    enabled = shouldProceed,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(shouldProceed) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        }
                    ),

                    onClick = {
                        //onclick should be provided to navigation root for back button behavior
                        onInvitationCreated()
                    }
                ) {
                    Text(stringResource(R.string.proceed))
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

                /*Row(
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
                }*/
            }
        }
    }
    //
}

@Composable
fun AddInvitationContent() {
    HouseholdRechargeTheme {
        AddInvitationScreen()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun AddInvitationDarkPreview() {
    AddInvitationContent()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun AddInvitationLightPreview() {
    AddInvitationContent()
}