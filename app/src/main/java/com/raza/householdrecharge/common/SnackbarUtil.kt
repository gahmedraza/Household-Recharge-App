package com.raza.householdrecharge.common

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState

object SnackbarUtil {

    suspend fun show(
        snackbarHostState: SnackbarHostState,
        message: String
    ) {
        snackbarHostState.showSnackbar(
            message = message,
            duration = SnackbarDuration.Long
        )
    }
}