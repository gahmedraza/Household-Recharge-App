package com.raza.householdrecharge.navigation

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
import com.raza.householdrecharge.HouseholdRechargeApplication
import com.raza.householdrecharge.ui.addhousehold.AddHouseholdScreen
import com.raza.householdrecharge.ui.addmember.AddMemberScreen
import com.raza.householdrecharge.ui.addrecharge.AddRechargeScreen
import com.raza.householdrecharge.common.AppViewModelFactory
import com.raza.householdrecharge.ui.dashbord.DashboardScreen
import com.raza.householdrecharge.ui.invitation.AddInvitationScreen
import com.raza.householdrecharge.ui.invitation.InvitationListingScreen
import com.raza.householdrecharge.ui.rechargehistory.RechargeListingScreen
import com.raza.householdrecharge.ui.settings.SettingScreen

@Composable
fun DashboardNavigationRoot() {
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
            startDestination = ComposeScreen.Dashboard.description,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(ComposeScreen.Dashboard.description) {
                DashboardScreen(
                    viewModel = viewModel(
                        factory = genericFactory
                    ),

                    onClick = { memberId, mobileNumber ->

                        navController.navigate("${ComposeScreen.RechargeHistory.description}/$memberId/$mobileNumber")
                    },

                    onAddMember = {
                        navController.navigate(ComposeScreen.AddRechargeHistory.description)
                    },
                    onDashboardClick = {
                        navController.navigate(ComposeScreen.Dashboard.description)
                    },
                    onRechargeHistoryClick = {

                    },
                    onSettingClick = {
                        navController.navigate(ComposeScreen.Setting.description)
                    }
                )
            }

            composable(
                route = "${ComposeScreen.RechargeHistory.description}/{memberId}/{mobileNumber}"
            ) { backStackEntry ->

                val memberId = backStackEntry.arguments?.getString("memberId") ?: ""
                val mobileNumber = backStackEntry.arguments?.getString("mobileNumber") ?: ""

                RechargeListingScreen(
                    memberId = memberId,

                    mobileNumber = mobileNumber,

                    viewModel = viewModel(
                        factory = genericFactory
                    ),

                    onSuccess = {
                        navController.navigate(ComposeScreen.Dashboard.description)
                    },

                    onFailure = {

                    },

                    onAddRecharge = { memberId, mobileNumber ->
                        navController.navigate("${ComposeScreen.AddRechargeHistory}/$memberId/$mobileNumber")
                    }
                )
            }

            composable(ComposeScreen.Setting.description) {
                SettingScreen(
                    viewModel = viewModel(
                        factory = genericFactory
                    ),

                    onAddMember = {
                        navController.navigate(ComposeScreen.AddMember.description)
                    },

                    onAddHousehold = {
                        navController.navigate(ComposeScreen.AddHousehold.description)
                    },

                    onSignOut = {
                        navController.navigate(ComposeScreen.OnboardingNavigation.description)
                    },
                    onInvitation = {
                        navController.navigate(ComposeScreen.Invitation.description)
                    }
                )
            }

            composable(ComposeScreen.OnboardingNavigation.description) {
                OnboardingNavigation()
            }

            composable(ComposeScreen.Invitation.description) {

                InvitationListingScreen(
                    viewModel = viewModel(
                        factory = genericFactory
                    ),

                    onAddInvitation = {
                        navController.navigate(ComposeScreen.AddInvitation.description)
                    }
                )
            }

            composable(ComposeScreen.AddInvitation.description) {
                AddInvitationScreen(
                    viewModel = viewModel(
                        factory = genericFactory
                    ),
                    onInvitationVerified = { invitationCode ->

                        navController.navigate(ComposeScreen.ConfirmHousehold.description)
                    }
                )
            }

            composable(ComposeScreen.AddMember.description) {
                AddMemberScreen(
                    viewModel = viewModel(
                        factory = genericFactory
                    ),

                    onSuccess = {

                        navController.navigate(ComposeScreen.Setting.description)
                    },

                    onFailure = {

                    }
                )
            }

            composable(
                route = "${ComposeScreen.AddRechargeHistory.description}/{memberId}/{mobileNumber}"
            ) { backStackEntry ->

                val memberId = backStackEntry.arguments?.getString("memberId") ?: ""
                val mobileNumber = backStackEntry.arguments?.getString("mobileNumber") ?: ""

                AddRechargeScreen(
                    memberId = memberId,

                    mobileNumber = mobileNumber,

                    viewModel = viewModel(
                        factory = genericFactory
                    ),

                    onSuccess = {

                        navController.navigate(ComposeScreen.DashboardNavigationRoot.description)
                    },

                    onFailure = {

                    }
                )
            }

            composable(ComposeScreen.AddHousehold.description) {
                AddHouseholdScreen(
                    viewModel = viewModel(
                        factory = genericFactory
                    ),

                    onSuccess = {

                        navController.navigate(ComposeScreen.Setting.description)
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
                navController.navigate(ComposeScreen.Dashboard.description)
            },
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = ComposeScreen.Dashboard.description
                )
            },
            label = {
                Text(ComposeScreen.Dashboard.description)
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("${ComposeScreen.RechargeHistory.description}//")
            },
            icon = {
                Icon(
                    Icons.Default.History,
                    contentDescription = ComposeScreen.RechargeHistory.description
                )
            },
            label = {
                Text(ComposeScreen.RechargeHistory.description)
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(ComposeScreen.Setting.description)
            },
            icon = {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = ComposeScreen.Setting.description
                )
            },
            label = {
                Text(ComposeScreen.Setting.description)
            }
        )
    }
}

val bottomBarRoutes = listOf(
    ComposeScreen.Dashboard.description,
    "${ComposeScreen.RechargeHistory.description}/{memberId}/{mobileNumber}",
    ComposeScreen.Setting.description
)