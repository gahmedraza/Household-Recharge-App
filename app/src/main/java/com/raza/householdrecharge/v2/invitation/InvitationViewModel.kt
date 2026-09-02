package com.raza.householdrecharge.v2.invitation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.v2.common.BaseViewModel
import com.raza.householdrecharge.v2.common.SessionManager
import com.raza.householdrecharge.v2.data.dto.InvitationDto
import com.raza.householdrecharge.v2.repository.FirestoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.security.SecureRandom

class InvitationViewModel(
    val sessionManager: SessionManager
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

            FirestoreRepository.createInvitationFacade(
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
            FirestoreRepository.fetchInvitationList(

                onSuccess = { invitationList ->
                    this@InvitationViewModel.invitationList = invitationList

                    onSuccess(invitationList)
                },

                onFailure = { error ->

                    onFailure(error)
                }
            )
        }
    }
}