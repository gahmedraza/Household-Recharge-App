package com.raza.householdrecharge.v2.navigation

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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.raza.householdrecharge.v1.HouseholdRechargeApplication
import com.raza.householdrecharge.v2.addhousehold.AddHouseholdScreen
import com.raza.householdrecharge.v2.addmember.AddMemberScreen
import com.raza.householdrecharge.v2.addrecharge.AddRechargeHistoryScreen
import com.raza.householdrecharge.v2.common.AppViewModelFactory
import com.raza.householdrecharge.v2.dashbord.DashboardScreen
import com.raza.householdrecharge.v2.invitation.AddInvitationScreen
import com.raza.householdrecharge.v2.invitation.ConfirmInvitationScreen
import com.raza.householdrecharge.v2.invitation.InvitationListingScreen
import com.raza.householdrecharge.v2.rechargehistory.RechargeHistoryScreen
import com.raza.householdrecharge.v2.settings.SettingScreen

@Composable
fun DashboardNavigation() {
    val navController = rememberNavController()

    val application = LocalContext.current.applicationContext
            as HouseholdRechargeApplication

    val genericFactory = AppViewModelFactory(
        application.sessionManager
    )

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
            startDestination = AppPages.Dashboard.description,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(AppPages.Dashboard.description) {
                DashboardScreen(
                    viewModel = viewModel(
                        factory = genericFactory
                    ),

                    onClick = { memberId, mobileNumber ->

                        navController.navigate("${AppPages.RechargeHistory.description}/$memberId/$mobileNumber")
                    },

                    onAddMember = {
                        navController.navigate(AppPages.AddRechargeHistory.description)
                    },
                    onDashboardClick = {
                        navController.navigate(AppPages.Dashboard.description)
                    },
                    onRechargeHistoryClick = {

                    },
                    onSettingClick = {
                        navController.navigate(AppPages.Setting.description)
                    }
                )
            }

            composable(
                route = "${AppPages.RechargeHistory.description}/{memberId}/{mobileNumber}"
            ) { backStackEntry ->

                val memberId = backStackEntry.arguments?.getString("memberId") ?: ""
                val mobileNumber = backStackEntry.arguments?.getString("mobileNumber") ?: ""

                RechargeHistoryScreen(
                    memberId = memberId,

                    mobileNumber = mobileNumber,

                    viewModel = viewModel(
                        factory = genericFactory
                    ),

                    onSuccess = {
                        navController.navigate(AppPages.Dashboard.description)
                    },

                    onFailure = {

                    },

                    onAddRecharge = { memberId, mobileNumber ->
                        navController.navigate("${AppPages.AddRechargeHistory}/$memberId/$mobileNumber")
                    }
                )
            }

            composable(AppPages.Setting.description) {
                SettingScreen(
                    viewModel = viewModel(
                        factory = genericFactory
                    ),

                    onAddMember = {
                        navController.navigate(AppPages.AddMember.description)
                    },

                    onAddHousehold = {
                        navController.navigate(AppPages.AddHousehold.description)
                    },

                    onSignOut = {
                        navController.navigate(AppPages.SignIn.description)
                    },
                    onInvitation = {
                        navController.navigate(AppPages.Invitation.description)
                    }
                )
            }

            composable(AppPages.Invitation.description) {

                InvitationListingScreen(
                    onAddInvitation = {
                        navController.navigate(AppPages.AddInvitation.description)
                    }
                )
            }

            composable(AppPages.AddInvitation.description) {
                AddInvitationScreen(
                    viewModel = viewModel(
                        factory = genericFactory
                    ),
                    onInvitationVerified = { invitationCode ->

                        navController.navigate(AppPages.ConfirmInvitation.description)
                    }
                )
            }

            composable(AppPages.ConfirmInvitation.description) {

                ConfirmInvitationScreen(
                    viewModel = viewModel(
                        factory = genericFactory
                    )
                )
            }

            composable(AppPages.AddMember.description) {
                AddMemberScreen(
                    viewModel = viewModel(
                        factory = genericFactory
                    ),

                    onSuccess = {

                        navController.navigate(AppPages.Setting.description)
                    },

                    onFailure = {

                    }
                )
            }

            composable(
                route = "${AppPages.AddRechargeHistory.description}/{memberId}/{mobileNumber}"
            ) { backStackEntry ->

                val memberId = backStackEntry.arguments?.getString("memberId") ?: ""
                val mobileNumber = backStackEntry.arguments?.getString("mobileNumber") ?: ""

                AddRechargeHistoryScreen(
                    memberId = memberId,

                    mobileNumber = mobileNumber,

                    viewModel = viewModel(
                        factory = genericFactory
                    ),

                    onSuccess = {

                        navController.navigate(AppPages.Main.description)
                    },

                    onFailure = {

                    }
                )
            }

            composable(AppPages.AddHousehold.description) {
                AddHouseholdScreen(
                    viewModel = viewModel(
                        factory = genericFactory
                    ),

                    onSuccess = {

                        navController.navigate(AppPages.Setting.description)
                    },

                    onFailure = {

                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    NavigationBar {

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(AppPages.Dashboard.description)
            },
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = AppPages.Dashboard.description
                )
            },
            label = {
                Text(AppPages.Dashboard.description)
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("${AppPages.RechargeHistory.description}//")
            },
            icon = {
                Icon(
                    Icons.Default.History,
                    contentDescription = AppPages.RechargeHistory.description
                )
            },
            label = {
                Text(AppPages.RechargeHistory.description)
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(AppPages.Setting.description)
            },
            icon = {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = AppPages.Setting.description
                )
            },
            label = {
                Text(AppPages.Setting.description)
            }
        )
    }
}

val bottomBarRoutes = listOf(
    AppPages.Dashboard.description,
    "${AppPages.RechargeHistory.description}/{memberId}/{mobileNumber}",
    AppPages.Setting.description
)