package com.raza.householdrecharge.v2.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raza.householdrecharge.HouseholdRechargeApplication
import com.raza.householdrecharge.v2.auth.SignInScreen
import com.raza.householdrecharge.v2.auth.SignupScreen
import com.raza.householdrecharge.v2.common.AppViewModelFactory
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
                    navController.navigate(AppPages.Main.description)
                },

                viewModel = viewModel(
                    factory = genericFactory
                )
            )
        }

        composable(AppPages.SignIn.description) {

            SignInScreen(
                viewModel = viewModel(
                    factory = genericFactory
                ),

                onSignup = {
                    Log.d("TAG", "OnSignup")
                    navController.navigate(AppPages.Signup.description)
                },

                onSignIn = {
                    Log.d("TAG", "OnSignIn")
                    navController.navigate(AppPages.Main.description)
                })
        }

        composable(AppPages.Signup.description) {

            SignupScreen(
                viewModel = viewModel(
                    factory = genericFactory
                ),

                householdViewModel = viewModel(
                    factory = genericFactory
                ),

                onSignup = {
                    Log.d("TAG", "OnSignup")
                    navController.navigate(AppPages.SignIn.description)
                },

                onSignIn = {
                    Log.d("TAG", "OnSignIn")
                    navController.navigate(AppPages.SignIn.description)
                })
        }

        composable(AppPages.Main.description) {
            DashboardNavigation()
        }
    }
}