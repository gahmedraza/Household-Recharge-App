package com.raza.householdrecharge.data.remote.mapper

import com.raza.householdrecharge.data.local.entity.RechargeEntity
import com.raza.householdrecharge.data.remote.dto.RechargeDto

object RechargeDtoMapper {

    fun map(rechargeEntity: RechargeEntity): RechargeDto {
        return RechargeDto(
            id = rechargeEntity.id,
            rechargeAmount = rechargeEntity.rechargeAmount,
            rechargeDate = rechargeEntity.rechargeDate,
            expiryDate = rechargeEntity.expiryDate,
            rechargedBy = rechargeEntity.rechargedBy,
            rechargeDescription = rechargeEntity.rechargeDescription,
            mobileNumber = rechargeEntity.mobileNumber,
            accountId = rechargeEntity.accountId,
            householdId = rechargeEntity.householdId
        )
    }

    fun map(rechargeEntityList: List<RechargeEntity>): List<RechargeDto> {
        return rechargeEntityList.map { rechargeEntity ->
            map(rechargeEntity)
        }
    }
}