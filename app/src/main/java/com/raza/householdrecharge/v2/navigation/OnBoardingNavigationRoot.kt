package com.raza.householdrecharge.v2.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raza.householdrecharge.v1.HouseholdRechargeApplication
import com.raza.householdrecharge.v2.auth.LoginScreen
import com.raza.householdrecharge.v2.auth.RegisterScreen
import com.raza.householdrecharge.v2.common.AppViewModelFactory
import com.raza.householdrecharge.v2.invitation.ConfirmHouseholdScreen
import com.raza.householdrecharge.v2.setuphousehold.CreateHouseholdScreen
import com.raza.householdrecharge.v2.setuphousehold.JoinHouseholdScreen
import com.raza.householdrecharge.v2.setuphousehold.SetupHouseholdScreen
import com.raza.householdrecharge.v2.splash.SplashScreen

@Composable
fun V2Navigation() {
    val navController = rememberNavController()

    val application = LocalContext.current.applicationContext
            as HouseholdRechargeApplication

    val genericFactory = AppViewModelFactory(
        application.sessionManager
    )

    NavHost(
        navController = navController,
        startDestination = AppPages.Splash.description
    ) {

        composable(AppPages.Splash.description) {

            SplashScreen(
                onUserNotFound = {
                    Log.d("TAG", "OnSplashFinished")
                    navController.navigate(AppPages.SignIn.description)
                },

                onUserFound = {
                    navController.navigate(AppPages.DashboardNavigationRoot.description)
                    //navController.navigate(AppPages.SetupHousehold.description)
                },

                viewModel = viewModel(
                    factory = genericFactory
                )
            )
        }

        composable(AppPages.SignIn.description) {

            LoginScreen(
                viewModel = viewModel(
                    factory = genericFactory
                ),

                onSignup = {
                    Log.d("TAG", "OnSignup")
                    navController.navigate(AppPages.Signup.description)
                },

                onSignInCompletion = {
                    Log.d("TAG", "OnSignIn")
                    navController.navigate(AppPages.DashboardNavigationRoot.description)
                },

                onBoardingNotComplete = {
                    Log.d("TAG", "Household Not Found")
                    navController.navigate(AppPages.SetupHousehold.description)
                }
            )
        }

        composable(AppPages.Signup.description) {

            RegisterScreen(
                viewModel = viewModel(
                    factory = genericFactory
                ),

                onSuccess = {
                    Log.d("TAG", "On Signup Success")
                    navController.navigate(AppPages.SetupHousehold.description)
                },

                onSignIn = {
                    Log.d("TAG", "OnSignIn")
                    navController.navigate(AppPages.SignIn.description)
                })
        }

        composable(AppPages.SetupHousehold.description) {
            SetupHouseholdScreen(
                viewModel = viewModel(
                    factory = genericFactory
                ),

                onJoinHousehold = {

                    navController.navigate(AppPages.JoinHousehold.description)
                },

                onCreateHousehold = {

                    navController.navigate(AppPages.CreateHousehold.description)
                }
            )
        }

        composable(AppPages.JoinHousehold.description) {
            JoinHouseholdScreen(
                viewModel = viewModel(
                    factory = genericFactory
                ),

                onSuccess = {

                    navController.navigate(AppPages.ConfirmHousehold.description)
                },

                onFailure = {}
            )
        }

        composable(AppPages.ConfirmHousehold.description) {

            ConfirmHouseholdScreen(
                viewModel = viewModel(
                    factory = genericFactory
                )
            )
        }

        composable(AppPages.CreateHousehold.description) {
            CreateHouseholdScreen(
                viewModel = viewModel(
                    factory = genericFactory
                ),

                onSuccess = {
                    navController.navigate(AppPages.DashboardNavigationRoot.description)
                },

                onFailure = {}
            )
        }

        composable(AppPages.DashboardNavigationRoot.description) {
            DashboardNavigationRoot()
        }
    }
}