package com.raza.householdrecharge.ui.splash

sealed class SplashDestination {
    data object Login: SplashDestination()
    data object SetupHousehold: SplashDestination()
    data object Dashboard: SplashDestination()
}