package com.raza.householdrecharge.presentation.common

object RechargedByValidator {

    fun validate(rechargedBy: String, rechargedByError: String): String {
        if (rechargedBy.isEmpty()) {
            return "recharge by cannot be empty"
        }

        if (rechargedBy.length < 4) {
            return "recharged by cannot be less than 4 characters"
        }

        if(rechargedBy.length > 24) {
            return "recharged by cannot exceed 24 characters"
        }

        return rechargedByError
    }
}