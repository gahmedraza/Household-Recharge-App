package com.raza.householdrecharge.presentation.invitation

data class InvitationUIState(
    var isLoading: Boolean = false,
    var invitationCode: String = "",
    var invitationList: List<Invitation> = emptyList()
)