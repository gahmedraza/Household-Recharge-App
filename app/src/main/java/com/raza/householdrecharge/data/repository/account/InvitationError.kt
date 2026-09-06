package com.raza.householdrecharge.data.repository.account

sealed class InvitationError {
    data object InvitationCodeNotFound: InvitationError() //"invitation code not found"
    data object InvitationAlreadyUsed: InvitationError() //"invitation code already used"
    data object InvitationExpired: InvitationError()//"invitation code has expired"

    data object UnknownError: InvitationError()

    data object InvitationDataMappingError: InvitationError()
}

const val PENDING = "pending"