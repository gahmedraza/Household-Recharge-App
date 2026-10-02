package com.raza.householdrecharge.app.navigation

import androidx.navigation.NavHostController

fun navigate(navController: NavHostController, route: String) {
    navController.popBackStack()
    navController.navigate(route)
}