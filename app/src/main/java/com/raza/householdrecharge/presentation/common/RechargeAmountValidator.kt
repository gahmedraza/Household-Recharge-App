package com.raza.householdrecharge.presentation.common

import javax.inject.Inject

class RechargeAmountValidator @Inject constructor() {

    fun validate(rechargeAmountString: String, rechargeAmountError: String): String {
        if(rechargeAmountString.isEmpty()) {
            return "amount cannot be empty"
        }

        val rechargeAmount = rechargeAmountString.toLong()

        if (rechargeAmount < 0) {
            return "amount cannot be less than 0"
        }

        if (rechargeAmount == 0L) {
            return "amount cannot be 0"
        }

        if (rechargeAmount > 10_000) {
            return "amount cannot be more than 10,000"
        }

        return rechargeAmountError
    }
}