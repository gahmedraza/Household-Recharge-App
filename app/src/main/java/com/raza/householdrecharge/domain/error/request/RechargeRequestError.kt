package com.raza.householdrecharge.domain.error.request

import com.raza.householdrecharge.domain.error.domain.HouseholdDomainError
import com.raza.householdrecharge.domain.error.domain.MobileNumberDomainError
import com.raza.householdrecharge.domain.error.domain.UserDomainError

sealed interface RechargeRequestError {
    data class UserError(val error: UserDomainError) : RechargeRequestError
    data class HouseholdError(val error: HouseholdDomainError) : RechargeRequestError
    data class MobileNumberError(val error: MobileNumberDomainError) : RechargeRequestError
}