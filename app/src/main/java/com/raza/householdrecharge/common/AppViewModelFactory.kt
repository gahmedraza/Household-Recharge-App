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
import com.raza.householdrecharge.data.repository.MobileNumberRepository
import com.raza.householdrecharge.data.repository.RechargeRepository
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.AuthUseCase
import com.raza.householdrecharge.domain.usecase.HouseholdUseCase
import com.raza.householdrecharge.domain.validator.RechargeValidator
import com.raza.householdrecharge.domain.validator.MobileNumberValidator
import com.raza.householdrecharge.presentation.addmember.AddMemberViewModel
import com.raza.householdrecharge.presentation.addrecharge.AddRechargeViewModel
import com.raza.householdrecharge.presentation.auth.LoginViewModel
import com.raza.householdrecharge.presentation.auth.RegisterViewModel
import com.raza.householdrecharge.presentation.auth.UserViewModel
import com.raza.householdrecharge.presentation.dashbord.DashboardViewModel
import com.raza.householdrecharge.presentation.invitation.InvitationViewModel
import com.raza.householdrecharge.presentation.mobilenumber.MobileNumberViewModel
import com.raza.householdrecharge.presentation.notification.NotificationViewModel
import com.raza.householdrecharge.presentation.recharge.RechargeListingViewModel
import com.raza.householdrecharge.presentation.settings.SettingViewModel
import com.raza.householdrecharge.presentation.setuphousehold.HouseholdViewModel
import com.raza.householdrecharge.presentation.splash.SplashViewModel

class AppViewModelFactory(
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {

    val rechargeValidator = RechargeValidator()

    val mobileNumberValidator = MobileNumberValidator()

    val firestore = FirebaseFirestore.getInstance()
    val firebaseAuth = FirebaseAuth.getInstance()

    val accountRepository = AccountRepository(firestore)
    val authRepository = AuthRepository(firebaseAuth)
    val householdRepository = HouseholdRepository(firestore)
    val invitationRepository = InvitationRepository(firestore)
    val memberRepository = MemberRepository(firestore)
    val rechargeRepository = RechargeRepository(firestore)
    val mobileNumberRepository = MobileNumberRepository(firestore)

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
                rechargeRepository = rechargeRepository,
                validator = rechargeValidator
            ) as T
        }

        if (modelClass.isAssignableFrom(MobileNumberViewModel::class.java)) {
            return MobileNumberViewModel(
                sessionManager = sessionManager,
                mobileNumberRepository = mobileNumberRepository,
                validator = mobileNumberValidator
            ) as T
        }

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                sessionManager = sessionManager,
                authRepository = authRepository,
                authUseCase = AuthUseCase(
                    authRepository = AuthRepository(firebaseAuth),
                    accountRepository = AccountRepository(firestore)
                )
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
                memberRepository = memberRepository,
                mobileNumberRepository = mobileNumberRepository
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
                rechargeRepository = rechargeRepository,
                validator = rechargeValidator
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
                householdUseCase = HouseholdUseCase(
                    householdRepository = HouseholdRepository(firestore),
                    accountRepository = accountRepository,
                    invitationRepository = invitationRepository
                ),
                authRepository = authRepository
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