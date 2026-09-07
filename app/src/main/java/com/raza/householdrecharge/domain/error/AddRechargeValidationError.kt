package com.raza.householdrecharge.domain.error

sealed class AddRechargeValidationError {

    data object UserNotFound : AddRechargeValidationError()//"user not found"

    data object HouseholdNotFound : AddRechargeValidationError()//"household not found"

    data object MemberNotFound : AddRechargeValidationError()//"No member found"

    data object MobileNumberNotFound : AddRechargeValidationError()//"No mobile number found"
}