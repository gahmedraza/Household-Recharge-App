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

        val result52 = findIfAccountHasALinkedHousehold(user.userId)

        if(result52 is Result.Failure) {
            return SplashDestination.Login
        }

        val linkingStatus = (result52 as Result.Success).data

        if(linkingStatus) {
            return SplashDestination.Dashboard
        } else {
            return SplashDestination.SetupHousehold
        }
    }

    fun isUserLoggedIn(): Boolean {
        val result = authRepository.getUser()

        if(result is Result.Failure) {

            return false
        }

        else if(
            result is Result.Success &&
            result.data == null) {

            return false
        }

        else if(
            result is Result.Success &&
            result.data != null
        ) {
            return true
        }

        else {

            return false
        }
    }

    suspend fun findIfAccountHasALinkedHousehold(
        accountId: String
    ): Result<Boolean, String> {

        var result: Result<Boolean, String>

        val result52 = householdUseCase.isAccountEligibleToJoinHousehold(
            accountId = accountId)

        if(result52 is Result.Failure) {
            result = Result.Success(false)

        } else {

            val isEligible = (result52 as Result.Success).data

            result = Result.Success(isEligible)
        }

        return result
    }
}

sealed class SplashDestination {
    data object Login: SplashDestination()
    data object SetupHousehold: SplashDestination()
    data object Dashboard: SplashDestination()
}