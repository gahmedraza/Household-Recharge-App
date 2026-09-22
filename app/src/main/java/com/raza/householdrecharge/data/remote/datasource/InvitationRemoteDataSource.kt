package com.raza.householdrecharge.data.remote.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.CollectionField
import com.raza.householdrecharge.data.remote.HouseholdCollection
import com.raza.householdrecharge.data.remote.dto.InvitationDto
import com.raza.householdrecharge.domain.error.InvitationError
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class InvitationRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun createInvitation(
        invitation: InvitationDto
    ): Result<String, String> {

        var result: Result<String, String>

        try {

            val documentReference = firestore
                .collection(HouseholdCollection.Invitations.description)
                .document()

            invitation.id = documentReference.id

            documentReference
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
    ): Result<List<InvitationDto>, String> {

        var result: Result<List<InvitationDto>, String>

        try {
            val documentSnapshot = firestore
                .collection(HouseholdCollection.Invitations.description)
                .get()
                .await()

            val invitationList = documentSnapshot.documents.mapNotNull { document ->
                document.toObject(InvitationDto::class.java)
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
                .collection(HouseholdCollection.Invitations.description)
                .whereEqualTo(CollectionField.InvitationCode.description, code.uppercase())
                .limit(1)
                .get()
                .await()

            if (snapshot == null || snapshot.documents.isEmpty()) {
                return Result.Failure(InvitationError.InvitationCodeNotFound)
            }

            val invitationDto = snapshot
                .documents[0]
                .toObject(InvitationDto::class.java)

            if (invitationDto == null) {
                result = Result.Failure(InvitationError.InvitationDataMappingError)

            } else {
                result = Result.Success(invitationDto)
            }

        } catch (e: Exception) {

            Logger.log(e.message)
            result = Result.Failure(InvitationError.UnknownError)
        }

        return result
    }

    suspend fun updateInvitation(
        invitation: InvitationDto
    ): Result<String, String> {

        var result: Result<String, String>

        try {

            val documentReference = firestore
                .collection(HouseholdCollection.Invitations.description)
                //this could also be done using id
                .whereEqualTo(CollectionField.InvitationCode.description, invitation.code)
                .limit(1)
                .get()
                .await()

            if (documentReference.documents.isEmpty()) {
                return Result.Failure("error")
            }

            documentReference.documents[0]
                .reference
                .set(invitation)
                .await()

            result = Result.Success("success")

        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }
}