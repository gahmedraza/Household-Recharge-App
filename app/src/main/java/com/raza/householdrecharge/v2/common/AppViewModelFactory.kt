package com.raza.householdrecharge.v2.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raza.householdrecharge.v2.addhousehold.AddHouseholdViewModel
import com.raza.householdrecharge.v2.addmember.AddMemberViewModel
import com.raza.householdrecharge.v2.addrecharge.AddRechargeViewModel
import com.raza.householdrecharge.v2.auth.LoginViewModel
import com.raza.householdrecharge.v2.auth.RegisterViewModel
import com.raza.householdrecharge.v2.dashbord.DashboardViewModel
import com.raza.householdrecharge.v2.invitation.InvitationViewModel
import com.raza.householdrecharge.v2.notification.NotificationViewModel
import com.raza.householdrecharge.v2.rechargehistory.RechargeListingViewModel
import com.raza.householdrecharge.v2.settings.SettingViewModel
import com.raza.householdrecharge.v2.setuphousehold.HouseholdViewModel
import com.raza.householdrecharge.v2.splash.SplashViewModel

class AppViewModelFactory(
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(AddHouseholdViewModel::class.java)) {
            return AddHouseholdViewModel(sessionManager) as T
        }

        if (modelClass.isAssignableFrom(AddMemberViewModel::class.java)) {
            return AddMemberViewModel(sessionManager) as T
        }

        if (modelClass.isAssignableFrom(AddRechargeViewModel::class.java)) {
            return AddRechargeViewModel(sessionManager) as T
        }

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(sessionManager) as T
        }

        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(sessionManager) as T
        }

        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(sessionManager) as T
        }

        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            return NotificationViewModel(sessionManager) as T
        }

        if (modelClass.isAssignableFrom(RechargeListingViewModel::class.java)) {
            return RechargeListingViewModel(sessionManager) as T
        }

        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(sessionManager) as T
        }

        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(sessionManager) as T
        }

        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(sessionManager) as T
        }

        if(modelClass.isAssignableFrom(HouseholdViewModel::class.java)) {
            return HouseholdViewModel(sessionManager) as T
        }

        if(modelClass.isAssignableFrom(InvitationViewModel::class.java)) {
            return InvitationViewModel(sessionManager) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}