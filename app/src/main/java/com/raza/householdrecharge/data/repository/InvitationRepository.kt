package com.raza.householdrecharge.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.InvitationDto
import com.raza.householdrecharge.ui.invitation.Invitation
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await

class InvitationRepository() {

    suspend fun createInvitation(
        invitation: InvitationDto
    ): Result<String, String> {

        return try {

            val documentReference = FirebaseFirestore
                .getInstance()
                .collection("invitations")
                .document(invitation.code)
                .set(invitation)
                .await()

            Result.Success("success")

        } catch (e: Exception) {

            Result.Failure(e.message.cleanString())
        }
    }

    suspend fun createInvitationFacade(
        invitation: InvitationDto,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val invitationResult = createInvitation(invitation)

        when(invitationResult) {
            is Result.Success -> {
                onSuccess("success")
            }
            is Result.Failure -> {
                onFailure("failure")
            }
        }
    }

    suspend fun fetchInvitationList(
        onSuccess: (List<Invitation>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        FirebaseFirestore
            .getInstance()
            .collection("invitations")
            .get()
            .addOnSuccessListener { result ->
                val invitationList = result.documents.mapNotNull { document ->
                    document.toObject(Invitation::class.java)
                }

                onSuccess(invitationList)
            }
            .addOnFailureListener { error ->

                onFailure(error.message.cleanString())
            }
    }
}