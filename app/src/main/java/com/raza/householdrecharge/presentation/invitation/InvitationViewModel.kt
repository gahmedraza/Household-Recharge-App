package com.raza.householdrecharge.presentation.invitation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.data.remote.dto.InvitationDto
import com.raza.householdrecharge.data.repository.InvitationRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.security.SecureRandom
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.presentation.common.BaseViewModel

class InvitationViewModel(
    val sessionManager: SessionManager,
    private val invitationRepository: InvitationRepository
): BaseViewModel() {

    var invitationCode by mutableStateOf("")
    var invitationList by mutableStateOf(listOf<Invitation>())

    fun createInvitation(
        code: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true

            val invitation = InvitationDto(
                code = code,
                householdId = sessionManager.householdId.first(),
                createdBy = sessionManager.authId.first(),
                createdAt = System.currentTimeMillis().toString(),
                expiresAt = (System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000L)).toString(),
                status = "pending"
            )

            invitationRepository.createInvitationFacade(
                invitation = invitation,
                onSuccess = { data ->
                    isLoading = false

                    onSuccess(data)
                },
                onFailure = { error ->
                    isLoading = false

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
        onSuccess: (List<Invitation>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        viewModelScope.launch {
            val invitationResult = invitationRepository.getAllInvitations()

            when(invitationResult) {
                is Result.Success<List<Invitation>> -> {
                    this@InvitationViewModel.invitationList = invitationResult.data

                    onSuccess(invitationResult.data)
                }
                is Result.Failure<String> -> {

                    onFailure(invitationResult.error)
                }
            }
        }
    }
}