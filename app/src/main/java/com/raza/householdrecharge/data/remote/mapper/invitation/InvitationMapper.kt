package com.raza.householdrecharge.data.remote.mapper.invitation

import com.raza.householdrecharge.data.local.entity.InvitationEntity
import com.raza.householdrecharge.domain.model.Invitation

object InvitationMapper {

    fun map(invitationEntity: InvitationEntity): Invitation {
        return Invitation(
            code = invitationEntity.code,
            householdId = invitationEntity.householdId,
            createdBy = invitationEntity.createdBy,
            createdAt = invitationEntity.createdAt,
            expiresAt = invitationEntity.expiresAt,
            status = invitationEntity.status,
            usedBy = invitationEntity.usedBy,
            usedAt = invitationEntity.usedAt
        )
    }

    fun map(invitationEntityList: List<InvitationEntity>): List<Invitation> {
        return invitationEntityList.map { invitationEntity ->
            map(invitationEntity)
        }
    }
}