package com.raza.householdrecharge.v2.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raza.householdrecharge.v2.addhousehold.AddHouseholdViewModel
import com.raza.householdrecharge.v2.addmember.AddMemberViewModel
import com.raza.householdrecharge.v2.addrecharge.AddRechargeHistoryViewModel
import com.raza.householdrecharge.v2.auth.SignInViewModel
import com.raza.householdrecharge.v2.auth.SignupViewModel
import com.raza.householdrecharge.v2.dashbord.DashboardViewModel
import com.raza.householdrecharge.v2.invitation.InvitationViewModel
import com.raza.householdrecharge.v2.notification.NotificationViewModel
import com.raza.householdrecharge.v2.rechargehistory.RechargeHistoryViewModel
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

        if (modelClass.isAssignableFrom(AddRechargeHistoryViewModel::class.java)) {
            return AddRechargeHistoryViewModel(sessionManager) as T
        }

        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(sessionManager) as T
        }

        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            return SignupViewModel(sessionManager) as T
        }

        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(sessionManager) as T
        }

        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            return NotificationViewModel(sessionManager) as T
        }

        if (modelClass.isAssignableFrom(RechargeHistoryViewModel::class.java)) {
            return RechargeHistoryViewModel(sessionManager) as T
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