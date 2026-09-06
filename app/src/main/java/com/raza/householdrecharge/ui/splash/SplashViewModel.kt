package com.raza.householdrecharge.ui.splash

import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.repository.AuthRepository
import com.raza.householdrecharge.domain.usecase.HouseholdUseCase
import com.raza.householdrecharge.ui.auth.AuthViewModel

class SplashViewModel(
    private val sessionManager: SessionManager,
    private val householdUseCase: HouseholdUseCase,
    private val authRepository: AuthRepository
) : AuthViewModel(sessionManager) {

    suspend fun getStartDestination(
    ): SplashDestination {

        val result51 = authRepository.getUser()

        if(result51 is Result.Failure) {
            return SplashDestination.Login
        }

        val user = (result51 as Result.Success).data

        val result53 = householdUseCase.isAccountEligibleToJoinHousehold(
            accountId = user.userId
        )

        if(result53 is Result.Failure) {
            return SplashDestination.Dashboard
        }

        val isEligible = (result53 as Result.Success).data

        if(isEligible) {
            return SplashDestination.SetupHousehold
        } else {
            return SplashDestination.Dashboard
        }
    }
}