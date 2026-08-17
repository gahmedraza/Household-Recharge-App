package com.raza.householdrecharge.ui

sealed class AppRoute(
    val route: String
) {
    data object Household: AppRoute("household")
    data object History: AppRoute("history/{memberId}") {
        fun create(memberId: Long): String =
            "history/$memberId"
    }
}