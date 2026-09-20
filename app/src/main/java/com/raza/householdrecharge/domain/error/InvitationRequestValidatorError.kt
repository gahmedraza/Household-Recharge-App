package com.raza.householdrecharge.domain.error

sealed class InvitationRequestValidatorError {
    data object UserNotFound : InvitationRequestValidatorError()
    data object HouseholdNotFound : InvitationRequestValidatorError()
    data object UnknownError : InvitationRequestValidatorError()
}