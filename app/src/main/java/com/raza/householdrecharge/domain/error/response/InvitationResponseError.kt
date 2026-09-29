package com.raza.householdrecharge.domain.error.response

import com.raza.householdrecharge.domain.error.usecase.HouseholdUseCaseError
import com.raza.householdrecharge.domain.error.usecase.InvitationUseCaseError

sealed class InvitationResponseError : ResponseError,
    HouseholdUseCaseError, InvitationUseCaseError {
    data object InvitationCodeNotFound : InvitationResponseError() //"invitation code not found"
    data object InvitationAlreadyUsed : InvitationResponseError() //"invitation code already used"
    data object InvitationExpired : InvitationResponseError()//"invitation code has expired"

    data object InvitationHouseholdMismatch: InvitationResponseError()

    data class UnknownError(val reason: String) : InvitationResponseError()

    data object InvitationDataMappingError : InvitationResponseError()
}