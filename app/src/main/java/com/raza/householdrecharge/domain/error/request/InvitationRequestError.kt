package com.raza.householdrecharge.domain.error.request

sealed interface InvitationRequestError : RequestError {
    data object InvitationNotFound : InvitationRequestError
}