package com.raza.householdrecharge.presentation.common

object InvitationCodeValidator {

    fun validateInvitationCode(invitationCode: String, invitationCodeError: String): String {
        if (invitationCode.isEmpty()) {
            return "Invitation Code cannot be empty"
        }

        if (invitationCode.length < 6) {
            return "Invitation code cannot be less than 6 characters"
        }

        if (invitationCode.length > 6) {
            return "Invitation code cannot be more than 6 characters"
        }

        return invitationCodeError
    }
}