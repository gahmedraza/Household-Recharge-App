package com.raza.householdrecharge.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raza.householdrecharge.presentation.login.LoginScreen
import com.raza.householdrecharge.presentation.register.RegisterScreen
import com.raza.householdrecharge.presentation.setuphousehold.ConfirmHouseholdScreen
import com.raza.householdrecharge.presentation.setuphousehold.CreateHouseholdScreen
import com.raza.householdrecharge.presentation.setuphousehold.FindHouseholdScreen
import com.raza.householdrecharge.presentation.setuphousehold.SetupHouseholdScreen
import com.raza.householdrecharge.presentation.splash.SplashScreen
import com.raza.householdrecharge.core.logging.Logger

@Composable
fun OnboardingNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ComposeScreen.Splash.description
    ) {

        composable(ComposeScreen.Splash.description) {

            SplashScreen(

                openLogin = {
                    navigate(navController, ComposeScreen.Login.description)
                },

                openSetupHousehold = {
                    navigate(navController, ComposeScreen.SetupHousehold.description)
                },

                openDashboard = {
                    navigate(navController, ComposeScreen.DashboardNavigationRoot.description)
                }
            )
        }

        composable(ComposeScreen.Login.description) {

            LoginScreen(
                onRegister = {
                    Logger.log("OnRegister")
                    navigate(navController, ComposeScreen.Register.description)
                },

                onLoginCompletion = {
                    Logger.log("OnLogin")
                    navigate(navController, ComposeScreen.DashboardNavigationRoot.description)
                },

                onBoardingNotComplete = {
                    Logger.log("Household Not Found")
                    navigate(navController, ComposeScreen.SetupHousehold.description)
                }
            )
        }

        composable(ComposeScreen.Register.description) {

            RegisterScreen(
                onSuccess = {
                    Logger.log("On Register Success")
                    navigate(navController, ComposeScreen.SetupHousehold.description)
                },

                onLogin = {
                    Logger.log("onLogin")
                    navigate(navController, ComposeScreen.Login.description)
                })
        }

        composable(ComposeScreen.SetupHousehold.description) {
            SetupHouseholdScreen(
                onJoinHousehold = {

                    navController.navigate(ComposeScreen.FindHousehold.description)
                },

                onCreateHousehold = {

                    navController.navigate(ComposeScreen.CreateHousehold.description)
                }
            )
        }

        composable(ComposeScreen.FindHousehold.description) {
            FindHouseholdScreen(
                onSuccess = { findHouseholdResponse ->

                    navigate(navController, "${ComposeScreen.ConfirmHousehold.description}/${findHouseholdResponse?.householdName}/${findHouseholdResponse?.householdId}/${findHouseholdResponse?.invitationCode}")
                }
            )
        }

        composable("${ComposeScreen.ConfirmHousehold.description}/{householdName}/{householdId}/{invitationCode}") { backstackEntry ->

            val householdName = backstackEntry.arguments?.getString("householdName") ?: ""
            val householdId = backstackEntry.arguments?.getString("householdId") ?: ""
            val invitationCode = backstackEntry.arguments?.getString("invitationCode") ?: ""

            ConfirmHouseholdScreen(
                householdName = householdName,

                householdId = householdId,

                invitationCode = invitationCode,

                onSuccess = {
                    navigate(navController, ComposeScreen.DashboardNavigationRoot.description)
                }
            )
        }

        composable(ComposeScreen.CreateHousehold.description) {
            CreateHouseholdScreen(
                onSuccess = {
                    navigate(navController, ComposeScreen.DashboardNavigationRoot.description)
                }
            )
        }

        composable(ComposeScreen.DashboardNavigationRoot.description) {
            DashboardNavigationRoot()
        }
    }
}