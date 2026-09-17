package com.raza.householdrecharge.domain.error

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