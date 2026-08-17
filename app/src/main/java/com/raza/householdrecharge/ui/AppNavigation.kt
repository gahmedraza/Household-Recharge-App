package com.raza.householdrecharge.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raza.householdrecharge.MemberViewModel
import com.raza.householdrecharge.R
import com.raza.householdrecharge.data.UserRole
import com.raza.householdrecharge.util.RequestHistoryScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    viewModel: MemberViewModel,
    userRole: UserRole
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.Household.route
    ) {
        composable(AppRoute.Household.route) {
            Scaffold(topBar = {
                TopAppBar(title = {
                    Text(stringResource(R.string.household)
                    )
                }
                )
            }
            ) { paddingValues ->

                HouseHoldContent(viewModel = viewModel,
                    userRole = userRole,
                    onHistory = { memberId ->
                        navController.navigate(
                            AppRoute.History.create(memberId)
                        )

                    },
                    modifier = Modifier
                        .padding(paddingValues)
                    )}}

                composable(
                    route = AppRoute.History.route,
                    arguments = listOf(
                        navArgument("memberId") {
                            type = NavType.LongType
                        }
                    )
                ) { backStackEntry ->

                    val memberId = backStackEntry.arguments?.getLong("memberId")
                        ?: return@composable

                    val requests by viewModel
                        .observeRequestHistory(memberId)
                        .collectAsState()

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        stringResource(R.string.history)
                                    )
                                },
                                navigationIcon = {
                                    IconButton(
                                        onClick = {
                                            navController.popBackStack()
                                        }
                                    ) {
                                        Icon(
                                            imageVector =
                                                Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = null
                                        )
                                    }
                                }
                            )
                        }
                    ) { paddingValues->

                        RequestHistoryScreen(
                            requests = requests,
                            modifier = Modifier
                                .padding(paddingValues)
                        )
                    }
                }

            }
        }

    //
    //composable(AppRoute.Household.route) {
    //            HouseHoldContent(
    //                viewModel= viewModel,
    //                userRole = userRole,
    //                onHistory = { memberId ->
    //                    navController.navigate(
    //                        AppRoute.History.create(memberId)
    //                    )
    //                }
    //            )
    //        }
    //
    //        composable(route = AppRoute.History.route,
    //            arguments = listOf(
    //                navArgument("memberId") {
    //                    type = NavType.LongType
    //                }
    //            )
    //        ) { backStackEntry ->
    //
    //            val memberId = backStackEntry.arguments?.getLong("memberId")?: return@composable
    //
    //            val requests by viewModel
    //                .observeRequestHistory(memberId)
    //                .collectAsState()
    //
    //            RequestHistoryScreen(
    //                requests = requests
    //            )
    //
    //        }
    //