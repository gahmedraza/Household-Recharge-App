package com.raza.householdrecharge.presentation.splash

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.repository.AuthRepository
import com.raza.householdrecharge.domain.usecase.HouseholdUseCase
import com.raza.householdrecharge.presentation.auth.AuthViewModel
import kotlinx.coroutines.launch

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

        val accountEligibilityDto = (result53 as Result.Success).data

        viewModelScope.launch {
            sessionManager.saveHouseholdLinkStatus(
                accountEligibilityDto.isEligible
            )
        }

        if(accountEligibilityDto.isEligible) {
            return SplashDestination.SetupHousehold
        } else {
            return SplashDestination.Dashboard
        }
    }
}