package com.raza.householdrecharge.domain.error.response

sealed class InvitationResponseError {
    data object InvitationCodeNotFound: InvitationResponseError() //"invitation code not found"
    data object InvitationAlreadyUsed: InvitationResponseError() //"invitation code already used"
    data object InvitationExpired: InvitationResponseError()//"invitation code has expired"

    data object UnknownError: InvitationResponseError()

    data object InvitationDataMappingError: InvitationResponseError()
}