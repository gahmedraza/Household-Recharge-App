package com.raza.householdrecharge.presentation.common

import com.raza.householdrecharge.util.isLessThanSpecifiedMonths
import com.raza.householdrecharge.util.isMoreThanSpecifiedMonths
import javax.inject.Inject

class RechargeDateValidator @Inject constructor() {

    fun validate(rechargeDateString: String, rechargeDateError: String): String {
        if(rechargeDateString.isEmpty()) {
            return "recharge date cannot be empty"
        }

        val rechargeDate = rechargeDateString.toLong()

        if (rechargeDate <= 0) {
            return "recharge date cannot be empty"
        }

        if (isLessThanSpecifiedMonths(
                date = rechargeDate,
                months = 1
            )
        ) {
            return "recharge date cannot be less than a month"
        }

        if (isMoreThanSpecifiedMonths(
                date = rechargeDate,
                months = 1
            )
        ) {
            return "recharge date cannot be more than a month"
        }

        return rechargeDateError
    }
}