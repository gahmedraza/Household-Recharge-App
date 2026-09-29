package com.raza.householdrecharge.data.remote.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.CollectionField
import com.raza.householdrecharge.data.remote.HouseholdCollection
import com.raza.householdrecharge.data.remote.dto.InvitationDto
import com.raza.householdrecharge.domain.error.response.InvitationResponseError
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class InvitationRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun createInvitation(
        invitation: InvitationDto
    ): Result<String, InvitationResponseError> {

        try {

            val documentReference = firestore
                .collection(HouseholdCollection.Invitations.description)
                .document()

            invitation.id = documentReference.id

            documentReference
                .set(invitation)
                .await()

            return Result.Success(
                "success"
            )

        } catch (e: Exception) {

            return Result.Failure(
                InvitationResponseError.UnknownError(
                    e.message.cleanString()
                )
            )
        }
    }

    suspend fun getAllInvitations(
    ): Result<List<InvitationDto>, InvitationResponseError> {

        try {
            val documentSnapshot = firestore
                .collection(HouseholdCollection.Invitations.description)
                .get()
                .await()

            val invitationList = documentSnapshot.documents.mapNotNull { document ->
                document.toObject(InvitationDto::class.java)
            }

            return Result.Success(
                invitationList
            )

        } catch (e: Exception) {

            return Result.Failure(
                InvitationResponseError.UnknownError(
                    e.message.cleanString()
                )
            )
        }
    }

    suspend fun getInvitationByInvitationCode(
        code: String
    ): Result<InvitationDto, InvitationResponseError> {

        try {

            val snapshot = firestore
                .collection(HouseholdCollection.Invitations.description)
                .whereEqualTo(CollectionField.InvitationCode.description, code.uppercase())
                .limit(1)
                .get()
                .await()

            if (snapshot == null || snapshot.documents.isEmpty()) {
                return Result.Failure(
                    InvitationResponseError.InvitationCodeNotFound
                )
            }

            val invitationDto = snapshot
                .documents[0]
                .toObject(InvitationDto::class.java)

            if (invitationDto == null) {
                return Result.Failure(
                    InvitationResponseError.InvitationDataMappingError
                )

            } else {
                return Result.Success(
                    invitationDto
                )
            }

        } catch (e: Exception) {

            //Logger.log() //todo
            return Result.Failure(
                InvitationResponseError.UnknownError(
                    e.message.cleanString()
                )
            )
        }
    }

    suspend fun updateInvitation(
        invitation: InvitationDto
    ): Result<String, InvitationResponseError> {

        try {

            val documentReference = firestore
                .collection(HouseholdCollection.Invitations.description)
                //this could also be done using id
                .whereEqualTo(CollectionField.InvitationCode.description, invitation.code)
                .limit(1)
                .get()
                .await()

            if (documentReference.documents.isEmpty()) {
                return Result.Failure(
                    InvitationResponseError.InvitationCodeNotFound
                )
            }

            documentReference.documents[0]
                .reference
                .set(invitation)
                .await()

            return Result.Success("success")

        } catch (e: Exception) {

            return Result.Failure(
                InvitationResponseError.UnknownError(
                    e.message.cleanString()
                )
            )
        }
    }
}