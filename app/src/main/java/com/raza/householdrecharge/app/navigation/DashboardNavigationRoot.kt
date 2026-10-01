package com.raza.householdrecharge.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
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
                    onDashboardCardClick = { mobileNumber, id ->

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
                        navController.navigate(ComposeScreen.Invitation.description)
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

            composable(ComposeScreen.Invitation.description) {

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

                        navController.navigate(ComposeScreen.Dashboard.description)
                    }
                )
            }

            composable(
                ComposeScreen.AddMobileNumber.description
            ) {

                AddMobileNumberScreen(
                    onSuccess = {

                        navController.navigate(ComposeScreen.Dashboard.description)
                    }
                )
            }
        }
    }
}