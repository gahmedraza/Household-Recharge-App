package com.raza.householdrecharge.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.data.repository.AccountRepository
import com.raza.householdrecharge.data.repository.AuthRepository
import com.raza.householdrecharge.data.repository.HouseholdRepository
import com.raza.householdrecharge.data.repository.InvitationRepository
import com.raza.householdrecharge.data.repository.MemberRepository
import com.raza.householdrecharge.data.repository.RechargeRepository
import com.raza.householdrecharge.domain.usecase.AuthUseCase
import com.raza.householdrecharge.domain.usecase.HouseholdUseCase
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

    val firestore = FirebaseFirestore.getInstance()
    val firebaseAuth = FirebaseAuth.getInstance()

    val accountRepository = AccountRepository(firestore)
    val authRepository = AuthRepository(firebaseAuth)
    val householdRepository = HouseholdRepository(firestore, firebaseAuth)
    val invitationRepository = InvitationRepository(firestore)
    val memberRepository = MemberRepository(firestore)
    val rechargeRepository = RechargeRepository(firestore)

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(AddMemberViewModel::class.java)) {
            return AddMemberViewModel(
                sessionManager = sessionManager,
                memberRepository = memberRepository
            ) as T
        }

        if (modelClass.isAssignableFrom(AddRechargeViewModel::class.java)) {
            return AddRechargeViewModel(
                sessionManager = sessionManager,
                rechargeRepository = rechargeRepository
            ) as T
        }

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                sessionManager = sessionManager,
                authRepository = authRepository
            ) as T
        }

        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(
                sessionManager = sessionManager,
                authUseCase = AuthUseCase(
                    authRepository = authRepository,
                    accountRepository = accountRepository
                )
            ) as T
        }

        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(
                sessionManager = sessionManager,
                memberRepository = memberRepository
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
                rechargeRepository = rechargeRepository
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
                householdRepository = householdRepository
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
                householdRepository = householdRepository,
                householdUseCase = HouseholdUseCase(
                    householdRepository = householdRepository,
                    accountRepository = accountRepository,
                    invitationRepository = invitationRepository
                ),
                invitationRepository = invitationRepository
            ) as T
        }

        if(modelClass.isAssignableFrom(InvitationViewModel::class.java)) {
            return InvitationViewModel(
                sessionManager = sessionManager,
                invitationRepository = invitationRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}