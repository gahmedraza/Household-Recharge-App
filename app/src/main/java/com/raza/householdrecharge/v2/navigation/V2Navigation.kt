package com.raza.householdrecharge.v2.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raza.householdrecharge.v2.addmember.MemberScreen
import com.raza.householdrecharge.v2.addrecharge.AddRechargeHistoryScreen
import com.raza.householdrecharge.v2.auth.SignInScreen
import com.raza.householdrecharge.v2.auth.SignupScreen
import com.raza.householdrecharge.v2.dashbord.DashboardScreen
import com.raza.householdrecharge.v2.rechargehistory.RechargeHistory
import com.raza.householdrecharge.v2.rechargehistory.RechargeHistoryScreen
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
                onNotLoggedIn = {
                    Log.d("TAG", "OnSplashFinished")
                    navController.navigate("SignIn")
                },

                onLoggedIn = {
                    navController.navigate("Dashboard")
                }
            )
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
            DashboardScreen(
                viewModel = viewModel(),
                onClick = {

                    navController.navigate("RechargeHistory")
                },
                onAddMember = {
                    navController.navigate("AddRechargeHistory")
                }
            )
        }

        composable("Member") {
            MemberScreen(
                viewModel = viewModel(),
                onSuccess = {

                },
                onFailure = {

                }
            )
        }

        composable("RechargeHistory") {
            RechargeHistoryScreen(
                viewModel = viewModel(),
                onSuccess = {
                    navController.navigate("Dashboard")
                },
                onFailure = {

                }
            )
        }

        composable("AddRechargeHistory") {
            AddRechargeHistoryScreen(
                viewModel = viewModel(),
                onSuccess = {

                    navController.navigate("Dashboard")
                },
                onFailure = {

                }
            )
        }
    }
}