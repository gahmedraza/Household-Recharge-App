package com.raza.householdrecharge.presentation.notification

import androidx.lifecycle.ViewModel
import com.raza.householdrecharge.data.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

}