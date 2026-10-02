package com.raza.householdrecharge.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    NavigationBar {

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(ComposeScreen.Dashboard.description) {

                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
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
                navController.navigate(ComposeScreen.RechargeListing.description) {

                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    Icons.Default.History,
                    contentDescription = ComposeScreen.RechargeListing.description
                )
            },
            label = {
                Text(ComposeScreen.RechargeListing.description)
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(ComposeScreen.Setting.description) {

                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
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