package com.raza.householdrecharge.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.raza.householdrecharge.presentation.account.AccountScreen
import com.raza.householdrecharge.presentation.addrecharge.AddRechargeScreen
import com.raza.householdrecharge.presentation.dashbord.DashboardScreen
import com.raza.householdrecharge.presentation.invitation.AddInvitationScreen
import com.raza.householdrecharge.presentation.invitation.InvitationListingScreen
import com.raza.householdrecharge.presentation.addmobilenumber.AddMobileNumberScreen
import com.raza.householdrecharge.presentation.recharge.RechargeListingScreen
import com.raza.householdrecharge.presentation.settings.SettingScreen

@Composable
fun DashboardNavigationRoot() {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if(currentRoute in bottomBarRoutes) {

                BottomNavigationBar(
                    navController = navController
                )
            }
        },
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = ComposeScreen.Dashboard.description,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(ComposeScreen.Dashboard.description) {
                DashboardScreen(

                    onAddRecharge = { mobileNumber, id ->
                        navController.navigate("${ComposeScreen.AddRecharge}/$mobileNumber/$id")
                    },

                    onAddMobileNumber = {
                        navController.navigate(ComposeScreen.AddMobileNumber.description)
                    }
                )
            }

            composable(ComposeScreen.RechargeListing.description) {
                RechargeListingScreen()
            }

            composable(ComposeScreen.Setting.description) {
                SettingScreen(

                    onLogout = {
                        navController.navigate(ComposeScreen.OnboardingNavigationRoot.description)
                    },

                    onInvitation = {
                        navController.navigate(ComposeScreen.InvitationListing.description)
                    },

                    onAccount = {
                        navController.navigate(ComposeScreen.Account.description)
                    }
                )
            }

            composable(ComposeScreen.Account.description) {
                AccountScreen()
            }

            composable(ComposeScreen.OnboardingNavigationRoot.description) {
                OnboardingNavigation()
            }

            composable(ComposeScreen.InvitationListing.description) {
                InvitationListingScreen(

                    onAddInvitation = {
                        navController.navigate(ComposeScreen.AddInvitation.description)
                    }
                )
            }

            composable(ComposeScreen.AddInvitation.description) {
                AddInvitationScreen(

                    onInvitationCreated = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = "${ComposeScreen.AddRecharge.description}/{mobileNumber}/{id}"
            ) { backStackEntry ->

                val mobileNumber = backStackEntry.arguments?.getString("mobileNumber") ?: ""
                val id = backStackEntry.arguments?.getString("id") ?: ""

                AddRechargeScreen(
                    mobileNumber = mobileNumber,
                    id = id,

                    onSuccess = {
                        navigate(navController, ComposeScreen.Dashboard.description)
                    }
                )
            }

            composable(
                ComposeScreen.AddMobileNumber.description
            ) {

                AddMobileNumberScreen(

                    onSuccess = {
                        navigate(navController, ComposeScreen.Dashboard.description)
                    }
                )
            }
        }
    }
}