package com.raza.householdrecharge.presentation.splash

sealed class SplashDestination {
    data object Login: SplashDestination()
    data object SetupHousehold: SplashDestination()
    data object Dashboard: SplashDestination()
}