package com.raza.householdrecharge.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raza.householdrecharge.ui.addhousehold.AddHouseholdViewModel
import com.raza.householdrecharge.ui.addmember.AddMemberViewModel
import com.raza.householdrecharge.ui.addrecharge.AddRechargeViewModel
import com.raza.householdrecharge.ui.auth.LoginViewModel
import com.raza.householdrecharge.ui.auth.RegisterViewModel
import com.raza.householdrecharge.ui.dashbord.DashboardViewModel
import com.raza.householdrecharge.ui.invitation.InvitationViewModel
import com.raza.householdrecharge.ui.notification.NotificationViewModel
import com.raza.householdrecharge.ui.rechargehistory.RechargeListingViewModel
import com.raza.householdrecharge.ui.settings.SettingViewModel
import com.raza.householdrecharge.ui.setuphousehold.HouseholdViewModel
import com.raza.householdrecharge.ui.splash.SplashViewModel

class AppViewModelFactory(
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(AddHouseholdViewModel::class.java)) {
            return AddHouseholdViewModel(
                sessionManager
            ) as T
        }

        if (modelClass.isAssignableFrom(AddMemberViewModel::class.java)) {
            return AddMemberViewModel(
                sessionManager
            ) as T
        }

        if (modelClass.isAssignableFrom(AddRechargeViewModel::class.java)) {
            return AddRechargeViewModel(
                sessionManager
            ) as T
        }

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(sessionManager) as T
        }

        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(
                sessionManager
            ) as T
        }

        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(
                sessionManager
            ) as T
        }

        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            return NotificationViewModel(
                sessionManager
            ) as T
        }

        if (modelClass.isAssignableFrom(RechargeListingViewModel::class.java)) {
            return RechargeListingViewModel(
                sessionManager
            ) as T
        }

        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(
                sessionManager
            ) as T
        }

        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(
                sessionManager
            ) as T
        }

        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(sessionManager) as T
        }

        if(modelClass.isAssignableFrom(HouseholdViewModel::class.java)) {
            return HouseholdViewModel(
                sessionManager
            ) as T
        }

        if(modelClass.isAssignableFrom(InvitationViewModel::class.java)) {
            return InvitationViewModel(
                sessionManager
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}