package com.raza.householdrecharge.domain.error

import com.raza.householdrecharge.data.repository.account.HouseholdError
import com.raza.householdrecharge.data.repository.account.InvitationError

sealed class HouseholdUseCaseError {
    data class Invitation(
        val error: InvitationError
    ) : HouseholdUseCaseError()

    data class Household(
        val error: HouseholdError
    ) : HouseholdUseCaseError()

    data class Unknown(
        val error: String
    ) : HouseholdUseCaseError()
}