package com.raza.householdrecharge.v2.auth

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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

@Composable
fun SignInScreen(
    onSignup: () -> Unit,
    onSignIn: () -> Unit
) {
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {

        Column {

            Spacer(modifier = Modifier.padding(20.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),

                onValueChange = {
                    mobileNumber = it
                },

                label = {
                    Text("Mobile Number")
                },

                value = mobileNumber
            )

            Spacer(modifier = Modifier.padding(20.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),

                onValueChange = {
                    password = it
                },

                label = {
                    Text("Password")
                },

                value = password
            )

            Spacer(modifier = Modifier.padding(120.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),

                onClick = {
                    onSignIn()
                }
            ) {
                Text("Sign In")
            }

            Spacer(modifier = Modifier.padding(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),

                onClick = {
                    onSignup()
                }
            ) {
                Text("Signup")
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun darkPreviewSignIn() {
    SignInScreen({}, {})
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun lightPreviewSignIn() {
    SignInScreen({}, {})
}