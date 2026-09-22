package com.raza.householdrecharge.presentation.common

//remove object, create class
object MobileNumberValidator {

    fun validateMobileNumber(mobileNumber: String, mobileNumberError: String): String {
        var mobileNumberError1 = mobileNumberError
        if(mobileNumber.isEmpty()) {
            mobileNumberError1 = "Mobile Number cannot be empty"
        }

        if (mobileNumber.length != 10) {
            mobileNumberError1 = "Mobile Number should contain 10 digits"
        }
        return mobileNumberError1
    }
}