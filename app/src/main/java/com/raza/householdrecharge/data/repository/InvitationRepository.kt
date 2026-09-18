package com.raza.householdrecharge.data.repository

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.datasource.InvitationRemoteDataSource
import com.raza.householdrecharge.data.remote.dto.InvitationDto
import com.raza.householdrecharge.domain.error.InvitationError
import javax.inject.Inject

class InvitationRepository @Inject constructor(
    private val invitationRemoteDataSource: InvitationRemoteDataSource
) {

    /**
     * Transit Method
     * No additional code
     */
    suspend fun createInvitation(
        invitation: InvitationDto
    ): Result<String, String> {

        return invitationRemoteDataSource.createInvitation(
            invitation
        )
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun createInvitationFacade(
        invitation: InvitationDto,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {

        return invitationRemoteDataSource.createInvitationFacade(
            invitation,
            onSuccess,
            onFailure
        )
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun getAllInvitations(
    ): Result<List<InvitationDto>, String> {

        return invitationRemoteDataSource.getAllInvitations()
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun getInvitationByInvitationCode(
        code: String
    ): Result<InvitationDto, InvitationError> {

        return invitationRemoteDataSource.getInvitationByInvitationCode(
            code
        )
    }
}