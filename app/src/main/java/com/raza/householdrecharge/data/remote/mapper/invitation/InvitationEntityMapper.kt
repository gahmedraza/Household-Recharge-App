package com.raza.householdrecharge.data.remote.mapper.invitation

import com.raza.householdrecharge.data.local.entity.InvitationEntity
import com.raza.householdrecharge.data.remote.dto.InvitationDto
import com.raza.householdrecharge.util.cleanString

object InvitationEntityMapper {

    fun map(invitationDto: InvitationDto): InvitationEntity {
        return InvitationEntity(
            code = invitationDto.code.cleanString(),
            householdId = invitationDto.householdId.cleanString(),
            createdBy = invitationDto.createdBy.cleanString(),
            createdAt = invitationDto.createdAt.cleanString(),
            expiresAt = invitationDto.expiresAt.cleanString(),
            status = invitationDto.status.cleanString()
        )
    }

    fun map(invitationDtoList: List<InvitationDto>): List<InvitationEntity> {
        return invitationDtoList.map { invitationDto ->
            map(invitationDto)
        }
    }
}