package com.raza.householdrecharge.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raza.householdrecharge.HouseholdRechargeApplication
import com.raza.householdrecharge.ui.auth.LoginScreen
import com.raza.householdrecharge.ui.auth.RegisterScreen
import com.raza.householdrecharge.common.AppViewModelFactory
import com.raza.householdrecharge.ui.setuphousehold.ConfirmHouseholdScreen
import com.raza.householdrecharge.ui.setuphousehold.CreateHouseholdScreen
import com.raza.householdrecharge.ui.setuphousehold.JoinHouseholdScreen
import com.raza.householdrecharge.ui.setuphousehold.SetupHouseholdScreen
import com.raza.householdrecharge.ui.splash.SplashScreen

@Composable
fun OnboardingNavigation() {
    val navController = rememberNavController()

    val application = LocalContext.current.applicationContext
            as HouseholdRechargeApplication

    val genericFactory = AppViewModelFactory(
        application.sessionManager
    )

    NavHost(
        navController = navController,
        startDestination = ComposeScreen.Splash.description
    ) {

        composable(ComposeScreen.Splash.description) {

            SplashScreen(
                onUserNotFound = {
                    Log.d("TAG", "OnSplashFinished")
                    navController.navigate(ComposeScreen.SignIn.description)
                },

                onUserFound = {
                    navController.navigate(ComposeScreen.DashboardNavigationRoot.description)
                    //navController.navigate(AppPages.SetupHousehold.description)
                },

                viewModel = viewModel(
                    factory = genericFactory
                )
            )
        }

        composable(ComposeScreen.SignIn.description) {

            LoginScreen(
                viewModel = viewModel(
                    factory = genericFactory
                ),

                onSignup = {
                    Log.d("TAG", "OnSignup")
                    navController.navigate(ComposeScreen.Signup.description)
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

        composable(ComposeScreen.Signup.description) {

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
                    navController.navigate(ComposeScreen.SignIn.description)
                })
        }

        composable(ComposeScreen.SetupHousehold.description) {
            SetupHouseholdScreen(
                viewModel = viewModel(
                    factory = genericFactory
                ),

                onJoinHousehold = {

                    navController.navigate(ComposeScreen.JoinHousehold.description)
                },

                onCreateHousehold = {

                    navController.navigate(ComposeScreen.CreateHousehold.description)
                }
            )
        }

        composable(ComposeScreen.JoinHousehold.description) {
            JoinHouseholdScreen(
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