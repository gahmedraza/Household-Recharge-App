package com.raza.householdrecharge.domain.usecase

import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.InvitationDto
import com.raza.householdrecharge.data.repository.InvitationRepository
import com.raza.householdrecharge.domain.error.response.InvitationResponseError
import com.raza.householdrecharge.domain.error.usecase.InvitationUseCaseError
import com.raza.householdrecharge.domain.model.Invitation
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InvitationUseCase @Inject constructor(
    private val invitationRepository: InvitationRepository
) {

    /**
     * Transit Method
     * No additional code
     */
    suspend fun createInvitation(
        invitation: InvitationDto
    ) : Result<String, InvitationUseCaseError> {

        return invitationRepository.createInvitation(
            invitation
        )
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun getAllInvitations(
    ) {

        return invitationRepository.getAllInvitations()
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun getInvitationByInvitationCode(
        code: String
    ): Result<InvitationDto, InvitationUseCaseError> {

        return invitationRepository.getInvitationByInvitationCode(
            code
        )
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun observeInvitations(): Flow<List<Invitation>> {

        return invitationRepository.observeInvitations()
    }
}