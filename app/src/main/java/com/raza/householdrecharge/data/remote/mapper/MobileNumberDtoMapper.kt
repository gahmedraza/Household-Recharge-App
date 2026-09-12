package com.raza.householdrecharge.data.remote.mapper

import com.raza.householdrecharge.data.local.entity.MobileNumberEntity
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto

object MobileNumberDtoMapper {

    fun map(mobileNumberEntity: MobileNumberEntity): MobileNumberDto {
        return MobileNumberDto(
            mobileNumber = mobileNumberEntity.mobileNumber,
            accountId = mobileNumberEntity.accountId,
            householdId = mobileNumberEntity.householdId
        )
    }

    fun map(mobileNumberEntityList: List<MobileNumberEntity>): List<MobileNumberDto> {
        return mobileNumberEntityList.map { mobileNumberEntity ->
            map(mobileNumberEntity)
        }
    }
}