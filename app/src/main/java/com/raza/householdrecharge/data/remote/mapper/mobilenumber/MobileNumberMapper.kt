package com.raza.householdrecharge.data.remote.mapper.mobilenumber

import com.raza.householdrecharge.data.local.entity.MobileNumberEntity
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto
import com.raza.householdrecharge.domain.model.MobileNumber

object MobileNumberMapper {

    fun map(mobileNumberDto: MobileNumberDto): MobileNumber {
        return MobileNumber(
            id = mobileNumberDto.id,
            mobileNumber = mobileNumberDto.mobileNumber,
            lastRechargeId = mobileNumberDto.lastRechargeId,
            accountId = mobileNumberDto.accountId,
            householdId = mobileNumberDto.householdId
        )
    }

    fun map(mobileNumberDtoList: List<MobileNumberDto>): List<MobileNumber> {
        return mobileNumberDtoList.map { mobileNumberDto ->
            map(mobileNumberDto)
        }
    }

    fun map(mobileNumberEntity: MobileNumberEntity): MobileNumber {
        return MobileNumber(
            id = mobileNumberEntity.id,
            mobileNumber = mobileNumberEntity.mobileNumber,
            lastRechargeId = mobileNumberEntity.lastRechargeId,
            accountId = mobileNumberEntity.accountId,
            householdId = mobileNumberEntity.householdId
        )
    }

    //todo modify to a better name
    fun map2(mobileNumberEntityList: List<MobileNumberEntity>): List<MobileNumber> {
        return mobileNumberEntityList.map { mobileNumberDto ->
            map(mobileNumberDto)
        }
    }
}