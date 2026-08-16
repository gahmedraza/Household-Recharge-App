package com.raza.householdrecharge.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raza.householdrecharge.R
import com.raza.householdrecharge.data.UserRole

@Composable
fun RoleScreen(
    onRoleSelected: (UserRole) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.household),
            style = MaterialTheme.typography.headlineMedium
        )

        Button(onClick = {
            onRoleSelected(UserRole.MANAGER)
        }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.manager))
        }

        Button(onClick = {
            onRoleSelected(UserRole.MEMBER)
        }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.member))
        }
    }
}