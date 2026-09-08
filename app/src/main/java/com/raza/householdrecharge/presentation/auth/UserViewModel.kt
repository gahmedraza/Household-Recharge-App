package com.raza.householdrecharge.presentation.auth

import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : BaseViewModel() {


}