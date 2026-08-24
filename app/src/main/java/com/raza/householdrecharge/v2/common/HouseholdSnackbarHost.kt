package com.raza.householdrecharge.v2.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable

@Composable
fun AppSnackbar(
    hostState: SnackbarHostState
) {
    SnackbarHost(
        hostState = hostState
    ) { data ->

        Snackbar(
            snackbarData = data,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            actionColor = MaterialTheme.colorScheme.primary
        )
    }
}