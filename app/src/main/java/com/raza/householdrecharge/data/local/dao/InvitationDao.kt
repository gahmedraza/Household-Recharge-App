package com.raza.householdrecharge.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.raza.householdrecharge.data.local.entity.InvitationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InvitationDao {

    @Upsert
    suspend fun upsertInvitation(invitationEntity: InvitationEntity)

    @Upsert
    suspend fun upsertInvitations(invitationEntityList: List<InvitationEntity>)

    @Query("update invitations set status = :status where code = :code")
    suspend fun updateInvitation(code: String, status: String)

    @Query("select * from invitations")
    fun observeInvitations(): Flow<List<InvitationEntity>>
}