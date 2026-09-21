package com.raza.householdrecharge.data.remote.mapper.household

import com.raza.householdrecharge.data.local.entity.HouseholdEntity
import com.raza.householdrecharge.data.remote.dto.HouseholdDto
import com.raza.householdrecharge.util.cleanString

object HouseholdEntityMapper {

    fun map(householdDto: HouseholdDto): HouseholdEntity {
        return HouseholdEntity(
            householdId = householdDto.householdId.cleanString(),
            householdName = householdDto.householdName.cleanString(),
            accountId = householdDto.accountId.cleanString()
        )
    }
}