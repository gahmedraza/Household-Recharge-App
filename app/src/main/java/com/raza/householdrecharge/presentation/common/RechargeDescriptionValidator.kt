package com.raza.householdrecharge.presentation.common

import javax.inject.Inject

class RechargeDescriptionValidator @Inject constructor() {

    fun validate(rechargeDescription: String, rechargeDescriptionError: String): String {
        if (rechargeDescription.isEmpty()) {
            return "recharge description cannot be empty"
        }

        if (rechargeDescription.length < 3) {
            return "recharge description cannot be less than 3 characters"
        }

        if (rechargeDescription.length > 24) {
            return "recharge description cannot be more than 24 characters"
        }

        return rechargeDescriptionError
    }
}