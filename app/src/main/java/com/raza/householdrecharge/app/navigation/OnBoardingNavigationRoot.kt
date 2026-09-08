package com.raza.householdrecharge.app.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raza.householdrecharge.presentation.auth.LoginScreen
import com.raza.householdrecharge.presentation.auth.RegisterScreen
import com.raza.householdrecharge.presentation.setuphousehold.ConfirmHouseholdScreen
import com.raza.householdrecharge.presentation.setuphousehold.CreateHouseholdScreen
import com.raza.householdrecharge.presentation.setuphousehold.FindHouseholdScreen
import com.raza.householdrecharge.presentation.setuphousehold.SetupHouseholdScreen
import com.raza.householdrecharge.presentation.splash.SplashScreen

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
                    navController.navigate(ComposeScreen.Login.description)
                },

                openSetupHousehold = {
                    navController.navigate(ComposeScreen.SetupHousehold.description)
                },

                openDashboard = {
                    navController.navigate(ComposeScreen.DashboardNavigationRoot.description)
                }
            )
        }

        composable(ComposeScreen.Login.description) {

            LoginScreen(
                onSignup = {
                    Log.d("TAG", "OnSignup")
                    navController.navigate(ComposeScreen.Register.description)
                },

                onSignInCompletion = {
                    Log.d("TAG", "OnSignIn")
                    navController.navigate(ComposeScreen.DashboardNavigationRoot.description)
                },

                onBoardingNotComplete = {
                    Log.d("TAG", "Household Not Found")
                    navController.navigate(ComposeScreen.SetupHousehold.description)
                }
            )
        }

        composable(ComposeScreen.Register.description) {

            RegisterScreen(
                onSuccess = {
                    Log.d("TAG", "On Signup Success")
                    navController.navigate(ComposeScreen.SetupHousehold.description)
                },

                onSignIn = {
                    Log.d("TAG", "OnSignIn")
                    navController.navigate(ComposeScreen.Login.description)
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
                onSuccess = { householdDto ->

                    navController.navigate("${ComposeScreen.ConfirmHousehold.description}/${householdDto?.householdName}/${householdDto?.householdId}/${householdDto?.invitationCode}")
                },

                onFailure = {}
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
                    navController.navigate(ComposeScreen.DashboardNavigationRoot.description)
                },

                onFailure = {}
            )
        }

        composable(ComposeScreen.CreateHousehold.description) {
            CreateHouseholdScreen(
                onSuccess = {
                    navController.navigate(ComposeScreen.DashboardNavigationRoot.description)
                },

                onFailure = {}
            )
        }

        composable(ComposeScreen.DashboardNavigationRoot.description) {
            DashboardNavigationRoot()
        }
    }
}