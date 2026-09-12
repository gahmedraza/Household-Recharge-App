package com.raza.householdrecharge.data.remote.mapper

import com.raza.householdrecharge.data.local.entity.MobileNumberEntity
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto

object MobileNumberEntity {

    fun map(mobileNumberDto: MobileNumberDto): MobileNumberEntity {
        return MobileNumberEntity(
            mobileNumber = mobileNumberDto.mobileNumber,
            accountId = mobileNumberDto.accountId,
            householdId = mobileNumberDto.householdId
        )
    }
}