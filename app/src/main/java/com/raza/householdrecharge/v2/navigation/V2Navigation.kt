package com.raza.householdrecharge.v2.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raza.householdrecharge.HouseholdRechargeApplication
import com.raza.householdrecharge.v2.addhousehold.AddHouseholdScreen
import com.raza.householdrecharge.v2.addmember.MemberScreen
import com.raza.householdrecharge.v2.addrecharge.AddRechargeHistoryScreen
import com.raza.householdrecharge.v2.auth.SignInScreen
import com.raza.householdrecharge.v2.auth.SignupScreen
import com.raza.householdrecharge.v2.common.AppViewModelFactory
import com.raza.householdrecharge.v2.dashbord.DashboardScreen
import com.raza.householdrecharge.v2.rechargehistory.RechargeHistoryScreen
import com.raza.householdrecharge.v2.settings.SettingScreen
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
        startDestination = "Splash"
    ) {

        composable("Splash") {

            SplashScreen(
                onUserNotFound = {
                    Log.d("TAG", "OnSplashFinished")
                    navController.navigate("SignIn")
                },

                onUserFound = {
                    navController.navigate("Dashboard")
                },

                viewModel = viewModel(
                    factory = genericFactory
                )
            )
        }

        composable("SignIn") {

            SignInScreen(
                viewModel = viewModel(
                    factory = genericFactory
                ),

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
                viewModel = viewModel(
                    factory = genericFactory
                ),

                householdViewModel = viewModel(
                    factory = genericFactory
                ),

                onSignup = {
                    Log.d("TAG", "OnSignup")
                    navController.navigate("SignIn")
                },

                onSignIn = {
                    Log.d("TAG", "OnSignIn")
                    navController.navigate("SignIn")
                })
        }

        composable("Dashboard") {
            DashboardScreen(
                viewModel = viewModel(
                    factory = genericFactory
                ),

                onClick = { memberId, mobileNumber ->

                    navController.navigate("RechargeHistory/$memberId/$mobileNumber")
                },

                onAddMember = {
                    navController.navigate("AddRechargeHistory")
                },
                onDashboardClick = {
                    navController.navigate("Dashboard")
                },
                onRechargeHistoryClick = {

                },
                onSettingClick = {
                    navController.navigate("SettingScreen")
                }
            )
        }

        composable("Member") {
            MemberScreen(
                viewModel = viewModel(
                    factory = genericFactory
                ),

                onSuccess = {

                },

                onFailure = {

                }
            )
        }

        composable(
            route = "RechargeHistory/{memberId}/{mobileNumber}"
        ) { backStackEntry ->

            val memberId = backStackEntry.arguments?.getString("memberId")?.toLongOrNull() ?: 0L
            val mobileNumber = backStackEntry.arguments?.getString("mobileNumber") ?: ""

            RechargeHistoryScreen(
                memberId = memberId,

                mobileNumber = mobileNumber,

                viewModel = viewModel(
                    factory = genericFactory
                ),

                onSuccess = {
                    navController.navigate("Dashboard")
                },

                onFailure = {

                },

                onAddRecharge = {
                    navController.navigate("AddRechargeHistory/$memberId/$mobileNumber")
                }
            )
        }

        composable(
            route = "AddRechargeHistory/{memberId}/{mobileNumber}"
        ) { backStackEntry ->

            val memberId = backStackEntry.arguments?.getString("memberId")?.toLongOrNull() ?: 0L
            val mobileNumber = backStackEntry.arguments?.getString("mobileNumber") ?: ""

            AddRechargeHistoryScreen(
                memberId = memberId,

                mobileNumber = mobileNumber,

                viewModel = viewModel(
                    factory = genericFactory
                ),

                onSuccess = {

                    navController.navigate("Dashboard")
                },

                onFailure = {

                }
            )
        }

        composable("SettingScreen") {
            SettingScreen(
                viewModel = viewModel(
                    factory = genericFactory
                ),

                onAddMember = {
                    navController.navigate("Member")
                },

                onAddHousehold = {
                    navController.navigate("AddHousehold")
                },

                onSignOut = {
                    navController.navigate("SignIn")
                }
            )
        }

        composable("AddHousehold") {
            AddHouseholdScreen(
                viewModel = viewModel(
                    factory = genericFactory
                ),

                onSuccess = {

                    navController.navigate("SettingScreen")
                },

                onFailure = {

                }
            )
        }
    }
}