package com.raza.householdrecharge.presentation.invitation

import com.raza.householdrecharge.data.remote.dto.InvitationDto

data class InvitationUIState(
    var isLoading: Boolean = false,
    var invitationCode: String = "",
    var invitationList: List<InvitationDto> = emptyList()
)