package com.raza.householdrecharge.app.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raza.householdrecharge.app.HouseholdRechargeApplication
import com.raza.householdrecharge.common.AppViewModelFactory
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

    val application = LocalContext.current.applicationContext
            as HouseholdRechargeApplication

    val genericFactory = AppViewModelFactory(
        sessionManager = application.sessionManager
    )

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
                },

                viewModel = viewModel(
                    factory = genericFactory
                )
            )
        }

        composable(ComposeScreen.Login.description) {

            LoginScreen(
                viewModel = viewModel(
                    factory = genericFactory
                ),

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
                viewModel = viewModel(
                    factory = genericFactory
                ),

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
                viewModel = viewModel(
                    factory = genericFactory
                ),

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
                viewModel = viewModel(
                    factory = genericFactory
                ),

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

                viewModel = viewModel(
                    factory = genericFactory
                ),

                onSuccess = {
                    navController.navigate(ComposeScreen.DashboardNavigationRoot.description)
                },

                onFailure = {}
            )
        }

        composable(ComposeScreen.CreateHousehold.description) {
            CreateHouseholdScreen(
                viewModel = viewModel(
                    factory = genericFactory
                ),

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