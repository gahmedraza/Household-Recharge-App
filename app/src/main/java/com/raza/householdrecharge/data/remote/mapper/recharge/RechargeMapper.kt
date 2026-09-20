package com.raza.householdrecharge.data.remote.mapper.recharge

import com.raza.householdrecharge.data.local.entity.RechargeEntity
import com.raza.householdrecharge.domain.model.Recharge

object RechargeMapper {

    fun map(rechargeEntity: RechargeEntity): Recharge {
        return Recharge(
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

    fun map(rechargeEntityList: List<RechargeEntity>): List<Recharge> {
        return rechargeEntityList.map { rechargeEntity ->
            map(rechargeEntity)
        }
    }
}