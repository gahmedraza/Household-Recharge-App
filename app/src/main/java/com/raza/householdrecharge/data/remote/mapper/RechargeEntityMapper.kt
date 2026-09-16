package com.raza.householdrecharge.data.remote.mapper

import com.raza.householdrecharge.core.logging.log
import com.raza.householdrecharge.data.local.entity.RechargeEntity
import com.raza.householdrecharge.data.remote.dto.RechargeDto

const val TAG = "RechargeEntityMapper"
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
        log(TAG, "size of input list: ${rechargeDtoList.size}")

        rechargeDtoList.forEach { rechargeDto ->
            if(rechargeDto.id.trim().isNotBlank()) {
                val rechargeEntity = map(rechargeDto)

                rechargeEntityList.add(rechargeEntity)
            } else {
                log(TAG, "recharge with empty id skipped")
            }
        }

        log(TAG, "size of output list: ${rechargeEntityList.size}")

        return rechargeEntityList

        /*return rechargeDtoList.map { rechargeDto ->
            map(rechargeDto)
        }*/
    }
}