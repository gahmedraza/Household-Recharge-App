package com.raza.householdrecharge.presentation.common

//remove object, create class
object MobileNumberValidator {

    fun validateMobileNumber(mobileNumber: String, mobileNumberError: String): String {
        if(mobileNumber.isEmpty()) {
            return "Mobile Number cannot be empty"
        }

        if (mobileNumber.length < 10) {
            return "Mobile Number cannot be less than 10 characters"
        }

        if (mobileNumber.length > 10) {
            return "Mobile Number cannot be more than 10 characters"
        }

        return mobileNumberError
    }
}