package com.raza.householdrecharge.v2.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raza.householdrecharge.v2.auth.SignInScreen
import com.raza.householdrecharge.v2.auth.SignupScreen
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
                onSignup = {
                    Log.d("TAG", "OnSignup")
                    navController.navigate("Signup")
                },
                onSignIn = {
                    Log.d("TAG", "OnSignIn")
                    navController.navigate("SignIn")
                })
        }

        composable("Signup") {

            SignupScreen(
                onSignup = {
                    Log.d("TAG", "OnSignup")
                    navController.navigate("Signup")
                },
                onSignIn = {
                    Log.d("TAG", "OnSignIn")
                    navController.navigate("SignIn")
                })
        }
    }
}