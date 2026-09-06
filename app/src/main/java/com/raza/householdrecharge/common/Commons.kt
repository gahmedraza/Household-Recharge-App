package com.raza.householdrecharge.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun <T : ViewModel> getViewModel(
    modelClass: Class<T>
): T {

    val context = LocalContext.current

    val sessionManager = SessionManager(context)

    val factory = AppViewModelFactory(
        sessionManager = sessionManager
    )

    return viewModel(
        modelClass = modelClass,
        factory = factory
    )
}