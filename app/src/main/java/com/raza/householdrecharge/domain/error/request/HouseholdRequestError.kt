package com.raza.householdrecharge.domain.error.request

import com.raza.householdrecharge.domain.error.domain.UnhandledError
import com.raza.householdrecharge.domain.error.domain.UserDomainError

sealed interface HouseholdRequestError {
    data class UserError(val error: UserDomainError): HouseholdRequestError
    data class UncheckedError(val error: UnhandledError): HouseholdRequestError
}