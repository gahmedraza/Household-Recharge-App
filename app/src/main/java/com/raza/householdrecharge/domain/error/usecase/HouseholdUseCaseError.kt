package com.raza.householdrecharge.domain.error.usecase

import com.raza.householdrecharge.domain.error.response.HouseholdResponseError
import com.raza.householdrecharge.domain.error.response.InvitationResponseError

sealed class HouseholdUseCaseError {
    data class Invitation(
        val error: InvitationResponseError
    ) : HouseholdUseCaseError()

    data class Household(
        val error: HouseholdResponseError
    ) : HouseholdUseCaseError()

    data class Unknown(
        val error: String
    ) : HouseholdUseCaseError()
}