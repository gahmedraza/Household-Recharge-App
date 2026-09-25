package com.raza.householdrecharge.domain.error.request

import com.raza.householdrecharge.domain.error.domain.HouseholdDomainError
import com.raza.householdrecharge.domain.error.domain.MobileNumberDomainError
import com.raza.householdrecharge.domain.error.domain.UserDomainError

sealed interface MobileNumberRequestError {
    data class UserError(val error: UserDomainError) :
        MobileNumberRequestError

    data class HouseholdError(val error: HouseholdDomainError) :
        MobileNumberRequestError

    data class MobileNumberError(val error: MobileNumberDomainError) :
        MobileNumberRequestError
}