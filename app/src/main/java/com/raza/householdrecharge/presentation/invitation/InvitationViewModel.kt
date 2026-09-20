package com.raza.householdrecharge.presentation.invitation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.data.remote.dto.InvitationDto
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.InvitationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.security.SecureRandom
import javax.inject.Inject

@HiltViewModel
class InvitationViewModel @Inject constructor(
    val sessionManager: SessionManager,
    private val invitationUseCase: InvitationUseCase
): ViewModel() {

    var invitationUIState = MutableStateFlow(InvitationUIState())

    init {
        observeInvitations()
    }

    fun createInvitation(
        code: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            invitationUIState.update {
                it.copy(
                    isLoading = true
                )
            }

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
                    invitationUIState.update {
                        it.copy(
                            isLoading = false
                        )
                    }

                    onSuccess(data)
                },
                onFailure = { error ->
                    invitationUIState.update {
                        it.copy(
                            isLoading = false
                        )
                    }

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
    ) {
        viewModelScope.launch {
            invitationUseCase.getAllInvitations()
        }
    }

    fun observeInvitations() {
        viewModelScope.launch {
            invitationUseCase.observeInvitations().collect { invitationList ->

                invitationUIState.update {
                    it.copy(
                        invitationList = invitationList
                    )
                }
            }
        }
    }
}