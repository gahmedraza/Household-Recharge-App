package com.raza.householdrecharge.presentation.common

object MobileNumberValidator {

    fun validateMobileNumber(mobileNumber: String, mobileNumberError: String): String {
        var mobileNumberError1 = mobileNumberError
        if (mobileNumber.length != 10) {
            mobileNumberError1 = "Enter a valid mobile number"
        }
        return mobileNumberError1
    }
}