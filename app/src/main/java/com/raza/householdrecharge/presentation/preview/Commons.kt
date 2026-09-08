package com.raza.householdrecharge.presentation.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raza.householdrecharge.common.AppViewModelFactory
import com.raza.householdrecharge.data.session.SessionManager

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