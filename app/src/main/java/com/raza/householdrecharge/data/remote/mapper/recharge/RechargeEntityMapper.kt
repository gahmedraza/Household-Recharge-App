package com.raza.householdrecharge.data.remote.mapper.recharge

import com.raza.householdrecharge.core.logging.Logger
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
        val rechargeEntityList = mutableListOf<RechargeEntity>()
        Logger.log("size of input list: ${rechargeDtoList.size}")

        rechargeDtoList.forEach { rechargeDto ->
            if(rechargeDto.id.trim().isNotBlank()) {
                val rechargeEntity = map(rechargeDto)

                rechargeEntityList.add(rechargeEntity)
            } else {
                Logger.log("recharge with empty id skipped")
            }
        }

        Logger.log("size of output list: ${rechargeEntityList.size}")

        return rechargeEntityList

        /*return rechargeDtoList.map { rechargeDto ->
            map(rechargeDto)
        }*/
    }
}