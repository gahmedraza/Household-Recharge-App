package com.raza.householdrecharge.data.remote.mapper

import com.raza.householdrecharge.data.local.entity.MobileNumberEntity
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto

object MobileNumberDtoMapper {

    fun map(mobileNumberEntity: MobileNumberEntity): MobileNumberDto {
        return MobileNumberDto(
            id = mobileNumberEntity.id,
            mobileNumber = mobileNumberEntity.mobileNumber,
            accountId = mobileNumberEntity.accountId,
            householdId = mobileNumberEntity.householdId,
            lastRechargeId = mobileNumberEntity.lastRechargeId
        )
    }

    fun map(mobileNumberEntityList: List<MobileNumberEntity>): List<MobileNumberDto> {
        return mobileNumberEntityList.map { mobileNumberEntity ->
            map(mobileNumberEntity)
        }
    }
}