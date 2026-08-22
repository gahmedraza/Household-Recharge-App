package com.raza.householdrecharge.v2.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raza.householdrecharge.v2.auth.SignInScreen
import com.raza.householdrecharge.v2.auth.SignupScreen
import com.raza.householdrecharge.v2.dashbord.DashboardScreen
import com.raza.householdrecharge.v2.splash.SplashScreen

@Composable
fun V2Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Splash"
    ) {

        composable("Splash") {

            SplashScreen(
                onSplashFinished = {
                    Log.d("TAG", "OnSplashFinished")
                    navController.navigate("SignIn")
                })
        }

        composable("SignIn") {

            SignInScreen(
                viewModel = viewModel(),

                onSignup = {
                    Log.d("TAG", "OnSignup")
                    navController.navigate("Signup")
                },

                onSignIn = {
                    Log.d("TAG", "OnSignIn")
                    navController.navigate("Dashboard")
                })
        }

        composable("Signup") {

            SignupScreen(
                viewModel = viewModel(),

                onSignup = {
                    Log.d("TAG", "OnSignup")
                    navController.navigate("Signup")
                },

                onSignIn = {
                    Log.d("TAG", "OnSignIn")
                    navController.navigate("SignIn")
                })
        }

        composable("Dashboard") {
            DashboardScreen(viewModel = viewModel())
        }
    }
}