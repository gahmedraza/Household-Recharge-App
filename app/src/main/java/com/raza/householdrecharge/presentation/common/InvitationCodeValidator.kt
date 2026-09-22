package com.raza.householdrecharge.presentation.common

object InvitationCodeValidator {

    fun validateInvitationCode(invitationCode: String, invitationCodeError: String): String {
        var invitationCodeError1 = invitationCodeError
        if (invitationCode.isEmpty()) {
            invitationCodeError1 = "Invitation Code cannot be empty"
        }

        if (invitationCode.length != 6) {
            invitationCodeError1 = "Invitation code must be 6 characters"
        }
        return invitationCodeError1
    }
}