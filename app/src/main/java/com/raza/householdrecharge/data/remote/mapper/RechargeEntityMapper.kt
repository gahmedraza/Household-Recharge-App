package com.raza.householdrecharge.data.remote.mapper

import com.raza.householdrecharge.data.local.entity.RechargeEntity
import com.raza.householdrecharge.data.remote.dto.RechargeDto

object RechargeEntityMapper {

    fun map(rechargeDto: RechargeDto): RechargeEntity {
        return RechargeEntity(
            id = rechargeDto.id,
            rechargeAmount = rechargeDto.rechargeAmount,
            rechargeDate = rechargeDto.rechargeDate,
            expiryDate = rechargeDto.expiryDate,
            rechargedBy = rechargeDto.rechargedBy,
            rechargeDescription = rechargeDto.rechargeDescription,
            mobileNumber = rechargeDto.mobileNumber,
            accountId = rechargeDto.accountId,
            householdId = rechargeDto.householdId
        )
    }

    fun map(rechargeDtoList: List<RechargeDto>): List<RechargeEntity> {
        return rechargeDtoList.map { rechargeDto ->
            map(rechargeDto)
        }
    }
}