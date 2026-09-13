package com.raza.householdrecharge.data.remote.mapper

import com.raza.householdrecharge.data.local.entity.MobileNumberEntity
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto

object MobileNumberEntityMapper {

    fun map(mobileNumberDto: MobileNumberDto): MobileNumberEntity {
        return MobileNumberEntity(
            id = mobileNumberDto.id,
            mobileNumber = mobileNumberDto.mobileNumber,
            accountId = mobileNumberDto.accountId,
            householdId = mobileNumberDto.householdId
        )
    }

    fun map(mobileNumberDtoList: List<MobileNumberDto>): List<MobileNumberEntity> {
        val mobileNumberEntityList = mutableListOf<MobileNumberEntity>()

        mobileNumberDtoList.forEach { mobileNumberDto ->
            val mobileNumberEntity = map(mobileNumberDto)

            mobileNumberEntityList.add(mobileNumberEntity)
        }

        return mobileNumberEntityList
    }
}