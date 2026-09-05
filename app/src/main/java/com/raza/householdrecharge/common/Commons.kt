package com.raza.householdrecharge.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun <T : ViewModel> getViewModel(
    modelClass: Class<T>
): T {

    val context = LocalContext.current

    val sessionManager = SessionManager(context)

    val firestore = FirebaseFirestore.getInstance()

    val factory = AppViewModelFactory(
        sessionManager = sessionManager,
        firestore = firestore
    )

    return viewModel(
        modelClass = modelClass,
        factory = factory
    )
}