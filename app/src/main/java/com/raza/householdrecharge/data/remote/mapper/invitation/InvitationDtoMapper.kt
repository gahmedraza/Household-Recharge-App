package com.raza.householdrecharge.data.remote.mapper.invitation

import com.raza.householdrecharge.data.local.entity.InvitationEntity
import com.raza.householdrecharge.data.remote.dto.InvitationDto

object InvitationDtoMapper {

    fun map(invitationEntity: InvitationEntity): InvitationDto {
        return InvitationDto(
            code = invitationEntity.code,
            householdId = invitationEntity.householdId,
            createdBy = invitationEntity.createdBy,
            createdAt = invitationEntity.createdAt,
            expiresAt = invitationEntity.expiresAt,
            status = invitationEntity.status
        )
    }

    fun map(invitationEntityList: List<InvitationEntity>): List<InvitationDto> {
        return invitationEntityList.map { invitationEntity ->
            map(invitationEntity)
        }
    }
}