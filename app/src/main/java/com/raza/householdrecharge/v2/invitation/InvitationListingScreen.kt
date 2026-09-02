package com.raza.householdrecharge.v2.invitation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raza.householdrecharge.v2.common.LargeBodyText
import com.raza.householdrecharge.v2.common.LargeTitleText
import com.raza.householdrecharge.v2.common.TitleBar
import com.raza.householdrecharge.v2.common.getViewModel
import com.raza.householdrecharge.v2.splash.SplashViewModel

@Composable
fun InvitationListingScreen(
    viewModel: InvitationViewModel,
    onAddInvitation: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.fetchInvitationList(
            onSuccess = { invitationList ->
                viewModel.invitationList = invitationList
            },
            onFailure = {

            }
        )
    }

    Body(
        viewModel = viewModel,
        onAddInvitation = onAddInvitation
    )
}

@Composable
fun Body(
    viewModel: InvitationViewModel,
    onAddInvitation: () -> Unit
) {
    Scaffold(
        topBar = {
            TitleBar("Invitations")
        },
        floatingActionButton = {
            if (true) {
                FloatingActionButton(
                    onClick = {
                        onAddInvitation()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Invitation"
                    )
                }
            }
        }
    ) { paddingValues ->

        InvitationList(
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel
        )
    }
}

@Composable
fun InvitationList(
    modifier: Modifier,
    viewModel: InvitationViewModel
) {

    LazyColumn(modifier = modifier) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }

        items(viewModel.invitationList) { invitation ->

            invitationCard(modifier, invitation)
        }
    }
}

@Composable
fun invitationCard(
    modifier: Modifier,
    invitation: Invitation
) {

    Card(
        modifier = Modifier
            .padding(
                top = 8.dp,
                bottom = 8.dp,
                start = 20.dp,
                end = 20.dp
            ),
        elevation = CardDefaults
            .cardElevation(
                defaultElevation = 2.dp
            ),
        shape = RoundedCornerShape(2.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 10.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 10.dp
                )

        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                LargeTitleText(
                    text = invitation.code
                )

                Spacer(modifier = Modifier.weight(1f))

                LargeBodyText(
                    text = invitation.status
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                LargeBodyText(
                    text = "Created:"
                )

                Spacer(modifier = Modifier.weight(1f))

                LargeBodyText(
                    text = invitation.dateCreated
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                LargeBodyText(
                    text = "Expires:"
                )

                Spacer(modifier = Modifier.weight(1f))

                LargeBodyText(
                    text = invitation.dateExpires
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Copy")
                }

                Spacer(modifier = Modifier.weight(1f))

                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Share")
                }
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun invitationScreenDarkPreview() {
    InvitationScreenContent()
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun invitationScreenLightPreview() {
    InvitationScreenContent()
}

@Composable
fun InvitationScreenContent() {
    val viewModel = getViewModel(InvitationViewModel::class.java)

    Body(
        viewModel = viewModel,
        onAddInvitation = {}
    )
}

data class Invitation(
    val code: String = "",
    val dateCreated: String = "",
    val dateCreatedMillis: Long = 0,
    val dateExpires: String = "",
    val dateExpiresMillis: Long = 0,
    val status: String = ""
)