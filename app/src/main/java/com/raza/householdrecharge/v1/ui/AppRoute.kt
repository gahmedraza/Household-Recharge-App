package com.raza.householdrecharge.v1.ui

sealed class AppRoute(
    val route: String
) {
    data object Manager: AppRoute("manager")
    data object Member: AppRoute("member")

    data object Household : AppRoute("household")
    data object History : AppRoute("history/{memberId}") {
        fun create(memberId: Long): String =
            "history/$memberId"
    }
}