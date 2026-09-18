package com.raza.householdrecharge.presentation.invitation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.InvitationDto
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.InvitationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.security.SecureRandom
import javax.inject.Inject

@HiltViewModel
class InvitationViewModel @Inject constructor(
    val sessionManager: SessionManager,
    private val invitationUseCase: InvitationUseCase
): ViewModel() {

    var invitationUIState by mutableStateOf(InvitationUIState())

    fun createInvitation(
        code: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            invitationUIState.isLoading = true

            val invitation = InvitationDto(
                code = code,
                householdId = sessionManager.householdId.first(),
                createdBy = sessionManager.authId.first(),
                createdAt = System.currentTimeMillis().toString(),
                expiresAt = (System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000L)).toString(),
                status = "pending"
            )

            invitationUseCase.createInvitationFacade(
                invitation = invitation,
                onSuccess = { data ->
                    invitationUIState.isLoading = false

                    onSuccess(data)
                },
                onFailure = { error ->
                    invitationUIState.isLoading = false

                    onFailure(error)
                }
            )
        }
    }

    fun generateInvitationCode(length: Int = 6): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = SecureRandom()

        return buildString {
            repeat(length) {
                append(characters[random.nextInt(characters.length)])
            }
        }
    }

    fun fetchInvitationList(
        onSuccess: (List<InvitationDto>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        viewModelScope.launch {
            val invitationResult = invitationUseCase.getAllInvitations()

            when(invitationResult) {
                is Result.Success<List<InvitationDto>> -> {
                    invitationUIState.invitationList = invitationResult.data

                    onSuccess(invitationResult.data)
                }
                is Result.Failure<String> -> {

                    onFailure(invitationResult.error)
                }
            }
        }
    }
}