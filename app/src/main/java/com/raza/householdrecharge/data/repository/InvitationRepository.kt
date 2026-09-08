package com.raza.householdrecharge.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.core.logging.log
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.InvitationDto
import com.raza.householdrecharge.data.repository.account.InvitationError
import com.raza.householdrecharge.presentation.invitation.Invitation
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class InvitationRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun createInvitation(
        invitation: InvitationDto
    ): Result<String, String> {

        var result: Result<String, String>

        try {

            val documentReference = firestore
                .collection("invitations")
                .document(invitation.code)
                .set(invitation)
                .await()

            result = Result.Success("success")

        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }

    /**
     * TODO remove method
     */
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

    suspend fun getAllInvitations(
    ): Result<List<Invitation>, String> {

        var result: Result<List<Invitation>, String>

        try {
            val documentSnapshot = firestore
                .collection("invitations")
                .get()
                .await()

            val invitationList = documentSnapshot.documents.mapNotNull { document ->
                document.toObject(Invitation::class.java)
            }

            result = Result.Success(invitationList)

        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }


        return result
    }

    suspend fun getInvitationByInvitationCode(
        code: String
    ): Result<InvitationDto, InvitationError> {

        var result: Result<InvitationDto, InvitationError>

        try {

            val snapshot = firestore
                .collection("invitations")
                .document(code.uppercase())
                .get()
                .await()

            if (!snapshot.exists()) {
                return Result.Failure(InvitationError.InvitationCodeNotFound)
            }

            val invitationDto = snapshot.toObject(InvitationDto::class.java)

            if(invitationDto == null) {
                result = Result.Failure(InvitationError.InvitationDataMappingError)

            } else {
                result = Result.Success(invitationDto)
            }

        } catch (e: Exception) {

            log(e.message)
            result = Result.Failure(InvitationError.UnknownError)
        }

        return result
    }
}