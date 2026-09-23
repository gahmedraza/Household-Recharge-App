package com.raza.householdrecharge.presentation.invitation

import com.raza.householdrecharge.domain.model.Invitation

data class InvitationUIState(
    var isLoading: Boolean = false,
    var invitationCode: String = "",
    var invitationList: List<Invitation> = emptyList(),
    var apiResponse: String = "",
    var showBottomSheet: Boolean = false
)