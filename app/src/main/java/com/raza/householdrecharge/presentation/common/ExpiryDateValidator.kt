package com.raza.householdrecharge.presentation.common

import com.raza.householdrecharge.util.isLessThanSpecifiedMonths
import com.raza.householdrecharge.util.isMoreThanSpecifiedMonths
import javax.inject.Inject

class ExpiryDateValidator @Inject constructor() {

    fun validate(expiryDateString: String, expiryDateError: String): String {
        if(expiryDateString.isEmpty()) {
            return "expiry date cannot be empty"
        }

        val expiryDate = expiryDateString.toLong()

        if (expiryDate <= 0) {
            return "expiry date cannot be empty"
        }

        if (isMoreThanSpecifiedMonths(
                date = expiryDate,
                months = 13
            )
        ) {
            return "expiry date cannot be more than 12 months"
        }

        if (isLessThanSpecifiedMonths(
                date = expiryDate,
                months = 1
            )
        ) {
            return "expiry date cannot be less than one month"
        }

        return expiryDateError
    }
}