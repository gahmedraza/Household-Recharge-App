package com.raza.householdrecharge.v2.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.v2.dashbord.DashboardViewModel

@Composable
fun <T : ViewModel> getViewModel(
    modelClass: Class<T>
): T {

    val context = LocalContext.current

    val sessionManager = SessionManager(context)

    val factory = AppViewModelFactory(
        sessionManager
    )

    val viewModel: DashboardViewModel = viewModel(
        factory = factory
    )

    return viewModel(
        modelClass = modelClass,
        factory = factory
    )
}