package com.raza.householdrecharge.data.repository

import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.local.dao.InvitationDao
import com.raza.householdrecharge.data.local.entity.InvitationEntity
import com.raza.householdrecharge.data.remote.datasource.InvitationRemoteDataSource
import com.raza.householdrecharge.data.remote.dto.InvitationDto
import com.raza.householdrecharge.data.remote.mapper.invitation.InvitationDtoMapper
import com.raza.householdrecharge.data.remote.mapper.invitation.InvitationEntityMapper
import com.raza.householdrecharge.domain.error.InvitationError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InvitationRepository @Inject constructor(
    private val invitationRemoteDataSource: InvitationRemoteDataSource,
    private val invitationDao: InvitationDao
) {

    /**
     * Transit Method
     * No additional code
     */
    suspend fun createInvitation(
        invitation: InvitationDto
    ): Result<String, String> {

        val result51 = invitationRemoteDataSource.createInvitation(
            invitation
        )

        if(result51 is Result.Failure) {
            return result51
        }

        val invitationEntity = InvitationEntityMapper.map(invitation)

        invitationDao.upsertInvitation(invitationEntity)

        return result51 //todo remove
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
    ) {
        val result51 = invitationRemoteDataSource.getAllInvitations()

        if(result51 is Result.Failure) {
            Logger.log(result51.error)
        }

        val invitationDtoList = (result51 as Result.Success).data

        val invitationEntityList = InvitationEntityMapper.map(invitationDtoList)

        invitationDao.upsertInvitations(invitationEntityList)
    }

    /**
     * Transit Method
     * No additional code
     */
    suspend fun getInvitationByInvitationCode(
        code: String
    ): Result<InvitationDto, InvitationError> {

        val result51 = invitationRemoteDataSource.getInvitationByInvitationCode(
            code
        )

        if(result51 is Result.Failure) {
            return result51
        }

        val invitationDto = (result51 as Result.Success).data

        val invitationEntity = InvitationEntityMapper.map(invitationDto)

        invitationDao.upsertInvitation(invitationEntity)

        return result51
    }

    suspend fun observeInvitations(): Flow<List<InvitationDto>> {
        return invitationDao.observeInvitations().map { invitationEntityList ->
            InvitationDtoMapper.map(invitationEntityList)
        }
    }

    suspend fun updateInvitation(
        invitation: InvitationDto

    ): Result<String, String> {

        val result51 = invitationRemoteDataSource.updateInvitation(
            invitation
        )

        if(result51 is Result.Failure) {
            return result51
        }

        val invitationEntity = InvitationEntityMapper.map(invitation)

        invitationDao.upsertInvitation(invitationEntity)

        return result51 //todo remove
    }
}