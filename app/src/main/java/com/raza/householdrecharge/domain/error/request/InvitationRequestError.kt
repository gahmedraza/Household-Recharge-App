package com.raza.householdrecharge.domain.error.request

import com.raza.householdrecharge.domain.error.domain.HouseholdDomainError
import com.raza.householdrecharge.domain.error.domain.UserDomainError

sealed interface InvitationRequestError {
    data class UserError(val error: UserDomainError): InvitationRequestError
    data class HouseholdError(val error: HouseholdDomainError): InvitationRequestError
}