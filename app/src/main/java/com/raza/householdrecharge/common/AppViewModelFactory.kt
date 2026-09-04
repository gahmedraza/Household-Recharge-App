package com.raza.householdrecharge.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raza.householdrecharge.data.repository.AccountRepository
import com.raza.householdrecharge.data.repository.AuthRepository
import com.raza.householdrecharge.data.repository.HouseholdRepository
import com.raza.householdrecharge.data.repository.InvitationRepository
import com.raza.householdrecharge.data.repository.MemberRepository
import com.raza.householdrecharge.data.repository.RechargeRepository
import com.raza.householdrecharge.domain.usecase.AuthUseCase
import com.raza.householdrecharge.domain.usecase.HouseholdUseCase
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
                sessionManager = sessionManager,
                householdUseCase = HouseholdUseCase(
                    householdRepository = HouseholdRepository(),
                    accountRepository = AccountRepository()
                )
            ) as T
        }

        if (modelClass.isAssignableFrom(AddMemberViewModel::class.java)) {
            return AddMemberViewModel(
                sessionManager = sessionManager,
                memberRepository = MemberRepository()
            ) as T
        }

        if (modelClass.isAssignableFrom(AddRechargeViewModel::class.java)) {
            return AddRechargeViewModel(
                sessionManager = sessionManager,
                rechargeRepository = RechargeRepository()
            ) as T
        }

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                sessionManager = sessionManager,
                authRepository = AuthRepository()
            ) as T
        }

        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(
                sessionManager = sessionManager,
                authUseCase = AuthUseCase(
                    authRepository = AuthRepository(),
                    accountRepository = AccountRepository()
                )
            ) as T
        }

        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(
                sessionManager = sessionManager,
                memberRepository = MemberRepository()
            ) as T
        }

        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            return NotificationViewModel(
                sessionManager = sessionManager
            ) as T
        }

        if (modelClass.isAssignableFrom(RechargeListingViewModel::class.java)) {
            return RechargeListingViewModel(
                sessionManager = sessionManager,
                rechargeRepository = RechargeRepository()
            ) as T
        }

        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(
                sessionManager = sessionManager
            ) as T
        }

        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(
                sessionManager = sessionManager,
                householdRepository = HouseholdRepository()
            ) as T
        }

        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(
                sessionManager = sessionManager
            ) as T
        }

        if(modelClass.isAssignableFrom(HouseholdViewModel::class.java)) {
            return HouseholdViewModel(
                sessionManager = sessionManager,
                householdRepository = HouseholdRepository(),
                householdUseCase = HouseholdUseCase(
                    householdRepository = HouseholdRepository(),
                    accountRepository = AccountRepository()
                )
            ) as T
        }

        if(modelClass.isAssignableFrom(InvitationViewModel::class.java)) {
            return InvitationViewModel(
                sessionManager = sessionManager,
                invitationRepository = InvitationRepository()
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}